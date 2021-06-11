package com.jamesfchen.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 六月/10/2021  星期四
 */
class AndroidLogDetector : Detector(), SourceCodeScanner {

    override fun getApplicableMethodNames(): List<String> =
            listOf("tag", "format", "v", "d", "i", "w", "e", "wtf")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.util.Log")) {
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
                message = "android.util.Log usage is forbidden."
        )
    }

    companion object {
        private val IMPLEMENTATION = Implementation(
                AndroidLogDetector::class.java,
                Scope.JAVA_FILE_SCOPE
        )

        val ISSUE: Issue = Issue
                .create(
                        id = "AndroidLogDetector",
                        briefDescription = "The android Log should not be used",
                        explanation = """
                For amazing showcasing purposes we should not use the Android Log. We should the
                AmazingLog instead.
            """.trimIndent(),
                        category = Category.CORRECTNESS,
                        priority = 9,
                        severity = Severity.ERROR,
                        androidSpecific = true,
                        implementation = IMPLEMENTATION
                )
    }
}