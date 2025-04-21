package com.electrolytej.main.page.web

import android.util.Log
import com.electrolytej.GcTrigger
import com.electrolytej.collection.WeakHashSet
import java.lang.ref.WeakReference
import java.util.WeakHashMap

object Test {
    val set: MutableSet<TestClass> = WeakHashSet()
    val map : WeakHashMap<String, TestClass> = WeakHashMap()
    val w = WeakReference(TestClass("test6"))

    const val TAG = "WebActivity"
    fun addition_isCorrect() {
        val testClass1 = TestClass("test1")
        val testClass2 = TestClass("test2")
        val testClass3 = TestClass("test3")
        val testClass4 = TestClass("test4")
        val testClass5 = TestClass("test5")
        set.add(testClass1)
        set.add(testClass1)
        set.add(testClass1)
        set.add(testClass1)

        set.add(testClass2)
        set.add(testClass3)
        set.add(testClass4)
        set.add(testClass5)
        for (testClass in set) {
            Log.d(TAG,testClass.name)
        }


        val t6 = w.get()
        Log.d(TAG,"w.get() is $t6")
        // 建议JVM进行垃圾回收
//        System.gc()
//        Runtime.getRuntime().gc()
//        try {
//            // 给GC一点时间
//            Thread.sleep(2000)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
        GcTrigger.DEFAULT.runGc()
        val t6_ = w.get()
        Log.d(TAG,"w.get() is $t6_")

        val next = set.iterator().next();
        Log.d(TAG,"next: " + next?.name)
    }
    fun addition_isCorrect2() {
        val t6_ = w.get()
        Log.d(TAG,"w.get() is $t6_")
        try {
            val iterator = set.iterator()
            val hasNext = iterator.hasNext()
            Log.d(TAG,"hasNext: " + hasNext)
            while (hasNext) {
                val next = iterator.next()
                Log.d(TAG,"next: " + next)
            }

            for (testClass in set) {
                Log.d(TAG,testClass.name)
            }
        }catch (e: Exception){
            Log.d(TAG,Log.getStackTraceString(e))
        }

    }

    class TestClass(var name: String)
}