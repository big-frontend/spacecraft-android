package com.jamesfchen.bundle2

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        GlobalScope.launch {
            delay(10_000)
            withContext(Dispatchers.Default){
                println("main before")
                withContext<Unit>(Dispatchers.IO){

                    println("io")

                }
                println("main after")

            }

        }
        GlobalScope.launch {
            withContext(Dispatchers.Default){
                println("main2")
            }

        }
        Thread.sleep(20_000)
        assertEquals(4, 2 + 2)
    }
}