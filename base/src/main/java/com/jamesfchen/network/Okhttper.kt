package com.jamesfchen.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jamesfchen.base.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyStore
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class Okhttper(vararg interceptors: Interceptor) {
    companion object {
        //        @Volatile
//        private var sInstance: Okhttper? = null
//        private val lock: Any = Any()
//        fun get(): Okhttper {
//            if (sInstance == null) {
//                synchronized(lock) {
//                    if (sInstance == null) {
//                        sInstance = Okhttper()
//                    }
//                }
//            }
//            return sInstance!!
//        }
        fun create(vararg interceptors: Interceptor) = Okhttper(*interceptors)
    }

    val okHttpClient: OkHttpClient

    init {
        //android system ssl(openssl)
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).also {
                it.init(null as KeyStore?)
            }
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException(
                "Unexpected default trust managers:" + Arrays.toString(
                    trustManagers
                )
            )
        }
        val trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        val sslSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
//            .pingInterval(1, TimeUnit.SECONDS).retryOnConnectionFailure(true)
            //                .protocols(listOf(Protocol.HTTP_2))
            .retryOnConnectionFailure(true)
        //                .socketFactory(SocketFactory.getDefault())
//            .sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(OkHostnameVerifier)
//                .certificatePinner(DefaultCertificatePinner())
//                .eventListenerFactory(PrintingEventListener.FACTORY)
//                .eventListener()
//            .authenticator(DefaultAuthenticator()).dns(DefaultDns())
//                .proxy(DefaultProxy())
//                .proxyAuthenticator(DefaultProxyAuthenticator())
//                .proxySelector(DefaultProxySelector())
//            .dns(DefaultDns())
//                .proxy(DefaultProxy())
//                .proxyAuthenticator(DefaultProxyAuthenticator())
//                .proxySelector(DefaultProxySelector())
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        if (interceptors.isNotEmpty()) builder.interceptors().addAll(interceptors)
        builder.addNetworkInterceptor(StethoInterceptor())
//        builder.addNetworkInterceptor(MetricInterceptor())
        okHttpClient = builder.build()

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