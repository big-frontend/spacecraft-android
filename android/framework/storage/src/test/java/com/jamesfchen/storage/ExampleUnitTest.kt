package com.jamesfchen.storage

import android.content.Context
import org.junit.Assert
import org.junit.Test
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    var d: String by D()
    //延迟加载
    val d2:String by lazy {
        "cjf"
    }
    lateinit var d3:String//只能用于引用类型
    var d4:Int by Delegates.notNull()
    var d5 by Delegates.observable<Int>(3){ kProperty: KProperty<*>, i: Int, i1: Int ->

    }
    var d6 by Delegates.vetoable(4){ kProperty: KProperty<*>, i: Int, i1: Int -> false }
    //延迟加载

    val Context.dataStore by ReadOnlyProperty<Context, String> { thisRef, property -> "cjf" }
    fun test() {
//        Delegates.observable()
//        Delegates.vetoable()
        d = "cjf2"

    }
    @Test
    fun addition_isCorrect() {
//        DataStore.getInstance()
        Assert.assertEquals(4, (2 + 2).toLong())
    }
}
class D {
    operator fun getValue(dataStore: ExampleUnitTest, property: KProperty<*>): String {
        return "cjf"
    }

    operator fun setValue(dataStore: ExampleUnitTest, property: KProperty<*>, s: String) {

    }
}