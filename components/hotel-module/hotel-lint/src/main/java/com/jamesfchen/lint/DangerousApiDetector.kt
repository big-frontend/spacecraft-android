package com.jamesfchen.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import org.jetbrains.uast.UCallExpression

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 六月/10/2021  星期四
 *  * - Color.parseColor
 * - Integer.valueOf
 * - Integer.parseInt
 * - Long.parseLong
 * - Long.valueOf
 * - Float.valueOf
 * - Float.parseFloat
 * - Double.valueOf
 * - Double.parseDouble
 */
@Suppress("UnstableApiUsage")
class DangerousApiDetector : Detector(), SourceCodeScanner {

    override fun getApplicableMethodNames(): List<String> =
            listOf(
                    "parseColor",
                    "parseInt",
                    "parseLong",
                    "valueOf",
                    "parseFloat",
                    "parseDouble",
                    //kt
                    "toInt",
                    "toLong",
                    "toFloat",
                    "toDouble",
                    "toColorInt",
            )

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.graphics.Color")
                ||evaluator.isMemberInClass(method, "java.lang.Integer")
                ||evaluator.isMemberInClass(method, "java.lang.Long")
                ||evaluator.isMemberInClass(method, "java.lang.Float")
                ||evaluator.isMemberInClass(method, "java.lang.Double")
                ||evaluator.isMemberInClass(method, "kotlin.text.StringsKt__StringNumberConversionsJVMKt")
//                ||evaluator.isMemberInClass(method, "kotlin.text.StringsKt")
                ||evaluator.isMemberInClass(method, "androidx.core.graphics.ColorKt")
        ) {
            reportUsage(context, node)
        }
    }

    private fun reportUsage(context: JavaContext, node: UCallExpression) {
        context.report(
                issue = ISSUE,
                scope = node,
                location = context.getCallLocation(
                        call = node,
                        includeReceiver = true,
                        includeArguments = true
                ),
                message = "高危api为了你的绩效请使用SafeConverts"
        )
    }

    companion object {
        private val IMPLEMENTATION = Implementation(
                DangerousApiDetector::class.java,
                Scope.JAVA_FILE_SCOPE
        )

        val ISSUE: Issue = Issue
                .create(
                        id = "DangerousApiDetector",
                        briefDescription = "高危api检查器",
                        explanation = """
                高危api会抛出各种解析错误的异常，为了不是程序crash，更加友好的处理方式应该是返回null或者非正常状态让外部调用者处理
            """.trimIndent(),
                        category = Category.CORRECTNESS,
                        priority = 9,
                        severity = Severity.ERROR,
                        androidSpecific = true,
                        implementation = IMPLEMENTATION
                )
    }
}