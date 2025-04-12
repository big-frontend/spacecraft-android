package com.electrolytej;

import org.junit.Test;

import static org.junit.Assert.*;

import com.electrolytej.collection.WeakHashSet;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        Set<TestClass> set = new WeakHashSet<>();
        TestClass testClass1 = new TestClass("test1");
        TestClass testClass2 = new TestClass("test2");
        TestClass testClass3 = new TestClass("test3");
        TestClass testClass4 = new TestClass("test4");
        TestClass testClass5 = new TestClass("test5");
        set.add(testClass1);
        set.add(testClass1);
        set.add(testClass1);
        set.add(testClass1);

        set.add(testClass2);
        set.add(testClass3);
        set.add(testClass4);
        set.add(testClass5);
        for (TestClass testClass : set) {
            System.out.println(testClass.name);
        }

        WeakReference<TestClass> w = new WeakReference<>(new TestClass("test6"));
        TestClass t6 = w.get();
        System.out.println("w.get() is "+ t6);
        // 建议JVM进行垃圾回收
        System.gc();
        Runtime.getRuntime().gc();
        try {
            finalize();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        try {
            // 给GC一点时间
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GcTrigger.DEFAULT.runGc();
        TestClass t6_ = w.get();
        System.out.println("w.get() is "+ t6_);
//        TestClass next = set.iterator().next();
//        if (next ==null){
//            System.out.println("next is null");
//        }else {
//            System.out.println("next: " + next.name);
//        }

    }

    static class TestClass {
        public String name;

        public TestClass(String name) {
            this.name = name;
        }
    }
}