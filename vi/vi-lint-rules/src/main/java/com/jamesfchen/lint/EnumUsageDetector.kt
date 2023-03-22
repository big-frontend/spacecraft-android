package com.jamesfchen.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiType
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UField
import org.jetbrains.uast.UVariable

val ENUM_USAGE_EXPLANATION = """
        单个枚举会使应用的 classes.dex 文件增加大约 1.0 到 1.4KB 的大小。这些增加的大小会快速累积，产生复杂的系统或共享库。
        如果可能，请考虑使用 @IntDef 注解和代码缩减移除枚举并将它们转换为整数。此类型转换可保留枚举的各种安全优势
    """.trimIndent()
val ISSUE_ENUM_USAGE = Issue.create(
    "EnumUsage",
    "避免使用枚举",
    ENUM_USAGE_EXPLANATION,
    Category.PERFORMANCE,
    PRIORITY, Severity.WARNING,
    Implementation(EnumUsageDetector::class.java, Scope.JAVA_FILE_SCOPE),
)

class EnumUsageDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() =
        listOf<Class<out UElement>>(UVariable::class.java, UClass::class.java, UField::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitVariable(node: UVariable) = process(node.type, node)
        override fun visitField(node: UField) = process(node.type, node)
        override fun visitClass(node: UClass) = node.uastSuperTypes.forEach { process(it.type, it) }
        private fun process(type: PsiType, node: UElement) {
            if (type !is PsiClassType) return
            val enumClass = type.resolve()
            if (enumClass == null || !enumClass.isEnum) return
            context.report(
                ISSUE_ENUM_USAGE,
                node,
                context.getLocation(node),
                ENUM_USAGE_EXPLANATION
            )
        }
    }
}