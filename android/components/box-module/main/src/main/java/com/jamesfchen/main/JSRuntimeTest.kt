package com.jamesfchen.main

import com.eclipsesource.v8.JavaVoidCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.quickjs.*

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
        QuickJS.createRuntime().use { quickJS ->
            //创建主线程的EventQueue
            quickJS.createContext().use { context: JSContext ->

                //如果在主线程调用executeIntegerScript，则直接执行，如果在其他线程调用executeIntegerScript则会通过Handler#post切到主线程在调用
                val result: Int = context.executeIntegerScript("var a = 2+10;\n a;", "file.js")
                //js调用java在同一个线程
                context.registerJavaMethod(object : JavaCallback {
                    override fun invoke(receiver: JSObject?, args: JSArray?): Any {
                        return 1
                    }

                },"jsFunc1")
                context.registerJavaMethod({ receiver, args ->

                }, "jsFunc2")
                context.registerClass(object:JavaConstructorCallback{
                    override fun invoke(thisObj: JSObject?, args: JSArray?) {

                    }

                },"jsClass1")

            }

        }
    }
}