package com.jamesfchen.common

import android.util.Log
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/11/2019  Wed
 *
 * https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
 */
@RunWith(AndroidJUnit4::class)
class KotlinCoroutinesTest {
    val TAG = "KotlinCoroutinesTest"
    lateinit var client: OkHttpClient
    @Before
    fun setUp() {
        client = OkHttpClient.Builder()
                .build()

    }

    @Test
    fun testCoroutines() {
        val request = Request.Builder()
                .url("https://api.github.com/user".toHttpUrl())
                .build()

        GlobalScope.launch(Dispatchers.Main) {
//            val deferred = (1..1_000_000).map { n ->
//                GlobalScope.async {
//                    Log.i(TAG, "GlobalScope async")
//                    n
//                }
//            }
//            val sum = deferred.sumBy { it.await() }

            val deferred = GlobalScope.async(Dispatchers.IO) {
                delay(2000)
            }
            val deferred1 = GlobalScope.async(Dispatchers.IO) {
                delay(500)
            }
            val deferreds = listOf(deferred, deferred1)
            val awaitAll = deferreds.awaitAll()

        }

        Log.i(TAG, "testCoroutines:")
        try {
            Thread.sleep(30000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


    }

    suspend fun getImage(ts: Long): Long {
        return withContext(Dispatchers.IO) {

            delay(ts)
            ts
        }
    }


}