package com.jamesfchen.myhome.network

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.jamesfchen.network.DefaultAuthenticator
import com.jamesfchen.network.DefaultDns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.io.InputStreamReader
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

    suspend inline fun <reified T> sendRequest(r: Request): T =
        withContext(Dispatchers.IO) {
            val response = okHttpClient.newCall(r).execute()
            if (response.isSuccessful) {
                val reader = JsonReader(InputStreamReader(response.body?.byteStream()))
                return@withContext Gson().fromJson(reader, T::class.java)
            } else {
                throw IllegalStateException("发生错误code:${response.code}")
            }
        }

    suspend inline fun <reified T> sendRequest2(r: Request): T =
        suspendCancellableCoroutine { continuation ->
            val call = okHttpClient.newCall(r)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val reader = JsonReader(InputStreamReader(response.body?.byteStream()))
                        continuation.resume(Gson().fromJson(reader, T::class.java)) {
                            //协程被取消回调
                            Log.e("cjf", "协程被取消")
                        }

                    } else {
                        continuation.resumeWithException(IllegalStateException("发生错误code:${response.code}"))
                    }
                }
            })
            //协程被取消回调
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
}