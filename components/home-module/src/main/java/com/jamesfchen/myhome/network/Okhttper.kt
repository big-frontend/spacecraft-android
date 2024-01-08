package com.jamesfchen.myhome.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jamesfchen.network.DefaultDns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class Okhttper {
    val okHttpClient: OkHttpClient

    init {
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(StethoInterceptor())
//                .addNetworkInterceptor(MetricInterceptor())
            .dns(DefaultDns())
//                .proxy(DefaultProxy())
//                .proxyAuthenticator(DefaultProxyAuthenticator())
//                .proxySelector(DefaultProxySelector())
            .build()
    }

    companion object {
        private var httper: Okhttper? = null

        @Synchronized
        fun getInstance(): Okhttper {
            if (httper == null) httper = Okhttper()
            return httper!!
        }
    }

    suspend fun sendRequest(r: Request): Response =
        //创建一个在IO线程池中运行的block
        withContext(Dispatchers.IO) {
            val response = okHttpClient.newCall(r).execute()
            if (response.isSuccessful) {
                delay(4000)
                return@withContext response
            } else {
                throw IllegalStateException("发生错误code:${response.code}")
            }
        }

//    suspend fun sendRequest(r: Request): Response =
//        suspendCancellableCoroutine { continuation ->
//            val call = okHttpClient.newCall(r)
//            call.enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    continuation.resumeWithException(e)
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    if (response.isSuccessful) {
//                        Thread.sleep(4000)
//                        continuation.resume(response) {
//                            //协程被取消回调
//                            Log.e("cjf", "协程被取消")
//                        }
//                    } else {
//                        continuation.resumeWithException(IllegalStateException("发生错误code:${response.code}"))
//                    }
//                }
//            })
//            //协程被取消回调
//            continuation.invokeOnCancellation {
//                call.cancel()
//            }
//        }
}