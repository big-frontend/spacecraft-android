package com.jamesfchen.vi

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.jamesfchen.vi.apk.analyzer.ApkAnalyzerTask
import com.jamesfchen.vi.apk.minify.*
import com.tencent.matrix.plugin.compat.MatrixTraceCompat
import com.tencent.matrix.trace.extension.MatrixTraceExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

open class ViExtension()

class ViPlugin : Plugin<Project> {
    lateinit var project: Project
    val appExtension by lazy {
        return@lazy project.extensions.getByType(AppExtension::class.java)
    }

    override fun apply(project: Project) {
        this.project = project
        if (!project.plugins.hasPlugin("com.android.application")) {
            throw GradleException("Android Application plugin required.")
        }
//        project.extensions.extraProperties["bytex.forbidUseLenientMutationDuringGetArtifact"] = true
//        project.extensions.extraProperties["bytex.enableDuplicateClassCheck"] = false
        appExtension.buildTypes {
            it.getByName("debug") {
                it.isMinifyEnabled = false
                it.proguardFiles(
                    appExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            it.getByName("release") {
                it.isMinifyEnabled = true
                it.zipAlignEnabled(true)
                it.proguardFiles(
                    appExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        val vi = project.extensions.create("vi", ViExtension::class.java)
        val analyzerExtension = (vi as ExtensionAware).extensions.create("apkAnalyzer", ApkAnalyzerExtension::class.java)
        val minifyExtension = (vi as ExtensionAware).extensions.create("minifier", MinifyExtension::class.java)
        val codeminifyExtension = (minifyExtension as ExtensionAware).extensions.create("code", CodeMinifyExtension::class.java)
//        project.plugins.apply("bytex")
//        project.extensions.getByType(ByteXExtension::class.java).apply {
//            enable(true)
//            enableInDebug(false)
//            logLevel("DEBUG")
//        }
//        val constInlineExtension = (codeminifyExtension as ExtensionAware).extensions.create("constInline", ConstInlineExtension::class.java)
//        project.plugins.apply("bytex.const_inline")
//        project.plugins.apply("bytex.shrink_r_class")

//        val shrinkRExtension = (codeminifyExtension as ExtensionAware).extensions.create("rFiledInline", ShrinkRExtension::class.java)
//        val resourceCheckExtension = (shrinkRExtension as ExtensionAware).extensions.create("resCheck", ResourceCheckExtension::class.java)
//        val assetsCheckExtension = (shrinkRExtension as ExtensionAware).extensions.create("assetsCheck", AssetsCheckExtension::class.java)
        val resouceminifyExtension = (minifyExtension as ExtensionAware).extensions.create("resource", ResourceMinifyExtension::class.java)
        val imageExtension = (resouceminifyExtension as ExtensionAware).extensions.create("shrinkImage", ImageExtension::class.java)
        val sominifyExtension = (minifyExtension as ExtensionAware).extensions.create("so", SoMinifyExtension::class.java)
        val traceExtension = (vi as ExtensionAware).extensions.create("trace", MatrixTraceExtension::class.java)
        MatrixTraceCompat().inject(appExtension, project, traceExtension)
        project.afterEvaluate {
//            project.extensions.getByType(ConstInlineExtension::class.java).apply {
//                enable(constInlineExtension.isEnable)
//                enableInDebug(constInlineExtension.isEnableInDebug)
//                logLevel(constInlineExtension.logLevel.name)
//                isAutoFilterReflectionField = constInlineExtension.isAutoFilterReflectionField
//                isSkipWithRuntimeAnnotation = constInlineExtension.isSkipWithRuntimeAnnotation
//                skipWithAnnotations = constInlineExtension.skipWithAnnotations
//                whiteList = constInlineExtension.whiteList
//            }
//            project.extensions.getByType(ShrinkRExtension::class.java).apply {
//                enable(shrinkRExtension.isEnable)
//                enableInDebug(shrinkRExtension.isEnableInDebug)
//                logLevel(shrinkRExtension.logLevel.name)
//                keepList=  shrinkRExtension.keepList
//            }
//            project.extensions.getByType(ResourceCheckExtension::class.java).apply {
//                isEnable = resourceCheckExtension.isEnable
//                onlyCheck = resourceCheckExtension.onlyCheck
//                keepRes = resourceCheckExtension.keepRes
//            }
//            project.extensions.getByType(AssetsCheckExtension::class.java).apply {
//                isEnable = assetsCheckExtension.isEnable
//                keepBySuffix=assetsCheckExtension.keepBySuffix
//                keepAssets = assetsCheckExtension.keepAssets
//            }
            appExtension.applicationVariants.all { variant ->
                createMinifyTask(variant, minifyExtension, resouceminifyExtension,
                    imageExtension,
                    sominifyExtension,codeminifyExtension,
                )
                createApkAnalyzerTask(variant, analyzerExtension)
            }
        }

    }

    private fun createApkAnalyzerTask(variant: ApplicationVariant, analyzerExtension: ApkAnalyzerExtension) {
        if (analyzerExtension.variant.isEmpty() || analyzerExtension.variant.contains(variant.name)) {
            val tp = project.tasks.register("analyze${variant.name.capitalize()}Apk", ApkAnalyzerTask::class.java, analyzerExtension, variant)
            tp.configure {
                it.dependsOn("${project.path}:assemble${variant.name.capitalize()}")
                it.group = "vi"
            }
        }
    }

    private fun createMinifyTask(
        variant: ApplicationVariant,
        minifyExtension: MinifyExtension,
        resouceminifyExtension: ResourceMinifyExtension,
        imageExtension: ImageExtension,
        soMinifyExtension: SoMinifyExtension,
        codeMinifyExtension: CodeMinifyExtension,
    ) {
        if (minifyExtension.variant.isEmpty() || minifyExtension.variant.contains(variant.name)) {

            val tp = project.tasks.register("minify${variant.name.capitalize()}ResAndSo", MinifyTask::class.java,  variant,minifyExtension,resouceminifyExtension,
                imageExtension,
                soMinifyExtension,codeMinifyExtension
            )
            tp.configure {
                it.dependsOn(variant.packageApplicationProvider)
                it.group = "vi"
            }
            val autoSign = project.tasks.findByName("autoSign${variant.name.capitalize()}")
            if (autoSign != null) {
                autoSign.dependsOn(tp)
            } else {
                variant.assembleProvider?.configure {
                    it.dependsOn(tp)
                }
                variant.installProvider?.configure {
                    it.dependsOn(tp)
                }
            }
        }
    }
}