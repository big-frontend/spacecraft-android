package com.jamesfchen.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
val LOGUTILITY_EXPLANATION = """
        android.util.Log 不要使用，请用LogUtility
    """.trimIndent()
val ISSUE_LOGUTILITY: Issue = Issue
    .create(
        id = "AndroidLogDetector",
        briefDescription = "The android Log should not be used",
        explanation = LOGUTILITY_EXPLANATION,
        category = Category.CORRECTNESS,
        priority = 9,
        severity = Severity.ERROR,
        androidSpecific = true,
        implementation = Implementation(
            AndroidLogDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )
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
                issue = ISSUE_LOGUTILITY,
                scope = node,
                location = context.getCallLocation(
                        call = node,
                        includeReceiver = true,
                        includeArguments = true
                ),
                message = LOGUTILITY_EXPLANATION
        )
    }

}