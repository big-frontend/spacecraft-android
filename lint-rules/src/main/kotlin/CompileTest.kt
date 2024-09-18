package hello

import com.intellij.openapi.util.Disposer
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.analyzer.ModuleInfo
import org.jetbrains.kotlin.analyzer.ResolverForSingleModuleProject
import org.jetbrains.kotlin.analyzer.common.CommonAnalysisParameters
import org.jetbrains.kotlin.analyzer.common.CommonPlatformAnalyzerServices
import org.jetbrains.kotlin.analyzer.common.CommonResolverForModuleFactory
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoot
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.LanguageFeature
import org.jetbrains.kotlin.config.LanguageVersionSettings
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.container.tryGetService
import org.jetbrains.kotlin.context.ProjectContext
import org.jetbrains.kotlin.descriptors.ModuleCapability
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.platform.CommonPlatforms
import org.jetbrains.kotlin.platform.TargetPlatform
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.resolve.*
import org.jetbrains.kotlin.utils.PathUtil
import java.io.File
import java.util.*
import java.util.logging.Logger

/**
 * https://developer.aliyun.com/article/662337
 * kotlin源代码 --> 词法分析器 --> Token流 --> 语法分析器 --> 语法树/抽象语法树 -->语义分析器 --> 注解抽象语法树 --> 字节码生成器 ---> JVM字节码
 *
 * 1.词法分析器：使用JFlex开源库，_JetLexer(KotlinLexer)代表词法分析器
 * 2，语法分析器(syntax parser)：使用InteliJ项目中的PsiParser(KotlinParser),并且生成AST
 * 3.语义分析(semantic analyzer)：检查AST 上下文相关属性，并且生成中间代码。org.jetbrains.kotlin.resolve包下为语义分析，org.jetbrains.kotlin.ir包下为中间代码生成
 * 4.目标代码生成：org.jetbrains.kotlin.codegen
 *
 *
 * [k2视频讲述](https://blog.jetbrains.com/zh-hans/kotlin/2021/10/the-road-to-the-k2-compiler/)
 * 编译前端： build syntax tree and semantic info
 * 编译后端： generates target/machine code
 *                      frontend
 * source code --> [ parser  -- syntax tree ---> semantic analyzer ] -- syntax tree + semantic info -->
 *      backend
 * -->  [intermediate code:generator & optimizer -- intermediate representation --> machine code:generator & optimizer ] -- target/machine code-->
 *
 * kotlin在编译后端自动生成set/get代码(PropertyCodegen)，修改类为final
 *
 * ast解析经历
 * Lombok AST -> PSI -> UAST
 *
 *
 */
class KotlinScriptParser {
    private class SourceModuleInfo(
        override val name: Name,
        override val capabilities: Map<ModuleCapability<*>, Any?>,
        private val dependOnOldBuiltIns: Boolean,
        override val analyzerServices: PlatformDependentAnalyzerServices = CommonPlatformAnalyzerServices,
        override val platform: TargetPlatform = CommonPlatforms.defaultCommonPlatform
    ) : ModuleInfo {
        override fun dependencies() = listOf(this)

        override fun dependencyOnBuiltIns(): ModuleInfo.DependencyOnBuiltIns =
            if (dependOnOldBuiltIns) {
                ModuleInfo.DependencyOnBuiltIns.LAST
            } else {
                ModuleInfo.DependencyOnBuiltIns.NONE
            }
    }

    companion object {
        private val LOG = Logger.getLogger(KotlinScriptParser::class.java.name)

        private val messageCollector = object : MessageCollector {
            private var hasErrors = false
            override fun clear() {
                hasErrors = false
            }

            override fun hasErrors(): Boolean {
                return hasErrors
            }

            override fun report(
                severity: CompilerMessageSeverity,
                message: String,
                location: CompilerMessageSourceLocation?
            ) {
                val text = if (location != null) {
                    val path = location.path
                    val position = "$path: (${location.line}, ${location.column}) "
                    position + message
                } else {
                    message
                }

                when {
                    CompilerMessageSeverity.VERBOSE.contains(severity) -> {
                        LOG.finest(text)
                    }
                    severity == CompilerMessageSeverity.ERROR -> {
                        LOG.severe(text)
                        hasErrors = true
                    }
                    severity == CompilerMessageSeverity.INFO -> {
                        LOG.info(text)
                    }
                    else -> {
                        LOG.warning(text)
                    }
                }
            }
        }

        private val classPath: ArrayList<File> by lazy {
            val classpath = arrayListOf<File>()
            classpath += PathUtil.getResourcePathForClass(AnnotationTarget.CLASS.javaClass)
            classpath
        }
    }

    fun parse(vararg files: String): TopDownAnalysisContext {
        // The Kotlin compiler configuration
        val configuration = CompilerConfiguration()

        val groupingCollector = GroupingMessageCollector(messageCollector, false)
        val severityCollector = GroupingMessageCollector(groupingCollector, false)
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, severityCollector)

        configuration.addJvmClasspathRoots(PathUtil.getJdkClassesRootsFromCurrentJre())

        // The path to .kt files sources
        files.forEach { configuration.addKotlinSourceRoot(it) }
        // Configuring Kotlin class path
        configuration.addJvmClasspathRoots(classPath)

        val rootDisposable = Disposer.newDisposable()

        try {
            val environment = KotlinCoreEnvironment.createForProduction(
                rootDisposable,
                configuration,
                EnvironmentConfigFiles.JVM_CONFIG_FILES
            )
            val ktFiles = environment.getSourceFiles()

            val moduleInfo = SourceModuleInfo(Name.special("<main"), mapOf(), false)
            val project =
                ktFiles.firstOrNull()?.project ?: throw AssertionError("No files to analyze")

            val multiplatformLanguageSettings =
                object : LanguageVersionSettings by configuration.languageVersionSettings {
                    override fun getFeatureSupport(feature: LanguageFeature): LanguageFeature.State =
                        if (feature == LanguageFeature.MultiPlatformProjects) LanguageFeature.State.ENABLED
                        else configuration.languageVersionSettings.getFeatureSupport(feature)
                }

            val resolverForModuleFactory = CommonResolverForModuleFactory(
                CommonAnalysisParameters { content ->
                    environment.createPackagePartProvider(content.moduleContentScope)
                },
                CompilerEnvironment,
                CommonPlatforms.defaultCommonPlatform,
                shouldCheckExpectActual = false
            )

            val resolver = ResolverForSingleModuleProject(
                "sources for metadata serializer",
                ProjectContext(project, "metadata serializer"),
                moduleInfo,
                resolverForModuleFactory,
                GlobalSearchScope.allScope(project),
                languageVersionSettings = multiplatformLanguageSettings,
                syntheticFiles = ktFiles
            )

            val container = resolver.resolverForModule(moduleInfo).componentProvider

            val lazyTopDownAnalyzer =
                container.tryGetService(LazyTopDownAnalyzer::class.java) as LazyTopDownAnalyzer
            return lazyTopDownAnalyzer.analyzeDeclarations(
                TopDownAnalysisMode.TopLevelDeclarations,
                ktFiles
            )
        } finally {
            rootDisposable.dispose()
            if (severityCollector.hasErrors()) {
                throw RuntimeException("Compilation error")
            }
        }
    }
}


//fun main() {
//    val scriptFile = "/media/data/java/blackfern/kotlin-compile-test/test.kt"
//
//    val parser = KotlinScriptParser()
//
//    val analyzeContext = parser.parse(scriptFile)
//
//    val function = analyzeContext.functions.keys.first()
//    val body = function.bodyExpression as KtBlockExpression
//    val i = 0;
//}