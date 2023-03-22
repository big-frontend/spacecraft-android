package com.jamesfchen.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiType
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UVariable

val ANIMATION_DRAWABLE_EXPLANATION = """
        请勿使用 AnimationDrawable 创建逐帧动画，因为这样做需要为动画的每个帧添加单独的位图文件，而这会大大增加 APK 的大小.
        您应改为使用 AnimatedVectorDrawableCompat 创建动画矢量可绘制资源。
    """.trimIndent()
val ISSUE_ANIMATION_DRAWABLE_USAGE = Issue.create(
    "AnimationDrawableUsage",
    "将矢量图形用于动画图片",
    ANIMATION_DRAWABLE_EXPLANATION,
    Category.PERFORMANCE, PRIORITY, Severity.FATAL,
    implementation = Implementation(
        AnimationDrawableUsageDetector::class.java, Scope.JAVA_FILE_SCOPE
    ),
)

class AnimationDrawableUsageDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() =
        listOf<Class<out UElement>>(UVariable::class.java, UClass::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitVariable(node: UVariable) = process(node.type, node)

        override fun visitClass(node: UClass) = node.uastSuperTypes.forEach { process(it.type, it) }
        private fun process(type: PsiType, node: UElement) {
            if (!context.evaluator.typeMatches(type, "android.graphics.drawable.AnimationDrawable")) return
            context.report(
                ISSUE_ANIMATION_DRAWABLE_USAGE,
                node,
                context.getLocation(node),
                ANIMATION_DRAWABLE_EXPLANATION
            )
        }
    }

}