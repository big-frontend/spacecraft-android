package com.jamesfchen.main

import com.eclipsesource.v8.JavaVoidCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.quickjs.JSContext
import com.quickjs.QuickJS

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JSRuntimeTest {
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

    fun testQuickJs() {
        val quickJS = QuickJS.createRuntime()
        val context: JSContext = quickJS.createContext()
        val result: Int = context.executeIntegerScript("var a = 2+10;\n a;", "file.js")
        context.close()
        quickJS.close()
    }
}