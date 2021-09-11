@file:JvmName("RetrofitHelper")
package com.jamesfchen.myhome.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jamesfchen.myhome.network.api.WeatherApi
import com.jamesfchen.network.DefaultAuthenticator
import com.jamesfchen.network.DefaultDns
import com.jamesfchen.network.adapter.ObservableOrMainCallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.wire.WireConverterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 五月/21/2021  星期五
 */
object RetrofitHelper {
    @JvmStatic
    fun createWeatherApi(): WeatherApi {
        val (sslSocketFactory, trustManager) = createMetadata()
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .pingInterval(1, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .protocols(listOf(Protocol.HTTP_2))
                .addInterceptor(URLInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(StethoInterceptor())
//                .addNetworkInterceptor(MetricInterceptor())
//                .socketFactory(SocketFactory.getDefault())
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(OkHostnameVerifier)
//                .certificatePinner(DefaultCertificatePinner())
//                .eventListenerFactory(PrintingEventListener.FACTORY)
//                .eventListener()
                .authenticator(DefaultAuthenticator())
                .dns(DefaultDns())
//                .proxy(DefaultProxy())
//                .proxyAuthenticator(DefaultProxyAuthenticator())
//                .proxySelector(DefaultProxySelector())
                .build()
//        val client = UrlConnectionCallFactory()
        return Retrofit.Builder()
                .baseUrl("/")
                //                .baseUrl("http://localhost:50195")
//                .callFactory(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addConverterFactory(ProtoConverterFactory.create())
                .addConverterFactory(WireConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
    }


    data class SocketMetadata(val sslSocketFactory: SSLSocketFactory, val trustManager: X509TrustManager)

    private fun createMetadata(): SocketMetadata {

        //android system ssl(openssl)
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).also {
            it.init(null as KeyStore?)
        }
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        val sslSocketFactory = sslContext.socketFactory

        //third-party platform
        return SocketMetadata(sslSocketFactory, trustManager)
    }
}