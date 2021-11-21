package com.jamesfchen.kcp

import org.jetbrains.kotlin.test.Directives
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners
import org.jetbrains.kotlin.test.KotlinBaseTest
import org.junit.runner.RunWith
import java.io.File
import org.jetbrains.kotlin.test.*
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 */

@SuppressWarnings("all")
@RunWith(JUnit3RunnerWithInners::class)
class Test : KotlinBaseTest<KspTestFile>() {
    override fun doMultiFileTest(wholeFile: File, files: List<KspTestFile>) {

    }

    override fun createTestFilesFromFile(file: File, expectedText: String): List<KspTestFile> {
        TODO("Not yet implemented")
    }

}
class KspTestFile(
    name: String,
    content: String,
    directives: Directives,
//    var testModule: TestModule?
) : KotlinBaseTest.TestFile(name, content, directives)