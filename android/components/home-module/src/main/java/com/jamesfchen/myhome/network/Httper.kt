package com.jamesfchen.myhome.network

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.jamesfchen.myhome.model.L7
import com.jamesfchen.network.DefaultAuthenticator
import com.jamesfchen.network.DefaultDns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Httper {
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
        private var httper: Httper? = null

        @Synchronized
        fun getInstance(): Httper {
            if (httper == null) httper = Httper()
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