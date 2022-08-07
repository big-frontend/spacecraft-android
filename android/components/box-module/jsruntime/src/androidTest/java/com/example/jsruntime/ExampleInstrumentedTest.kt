package com.example.jsruntime

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eclipsesource.v8.V8
import com.quickjs.JSContext
import com.quickjs.QuickJS

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.jsruntime.test", appContext.packageName)
    }

    @Test
    fun testV8() {
        //创建JS上下文
        val rt: V8 = V8.createV8Runtime()
        //创建Java方法，并注册到JS上下文
        rt.registerJavaMethod({ receiver, parameters ->
            //相关处理
        }, "func")
        //执行JS
        val js = ""
        rt.executeScript(js)
        //释放资源
        rt.release()

    }

    @Test
    fun testQuickJs() {
        QuickJS.createRuntime().use { quickJS ->
            quickJS.createContext().use { context: JSContext ->
                val result: Int = context.executeIntegerScript("var a = 2+10;\n a;", "file.js")
            }
        }

    }
}