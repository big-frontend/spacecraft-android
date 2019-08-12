package com.hawksjamesf.network.source.remote.rest

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hawksjamesf.network.BuildConfig
import com.hawksjamesf.network.source.remote.rest.weather.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.wire.WireConverterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import kotlin.reflect.KClass

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
abstract class AbstractApi<T : Any> {
    protected abstract var api: T

    init {
        val baseUrl = if (getClass() === WeatherApi::class) {
            BuildConfig.WEATHER_APP_ID
        } else {
            BuildConfig.BASE_URL
        }


        val (sslSocketFactory, trustManager) = createMetadata()

        /***
         * 非对称密匙：
         * 对称密匙：
         */
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .pingInterval(1, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .protocols(listOf(Protocol.HTTP_2))
                .addInterceptor(URLInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(StethoInterceptor())
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
        api = Retrofit.Builder()
                .baseUrl(baseUrl)
                //                .baseUrl("http://localhost:50195")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
//                .addConverterFactory(ProtoConverterFactory.create())
                .addConverterFactory(WireConverterFactory.create())
                .build()
                .create(getClass().java)
    }

    abstract fun getClass(): KClass<T>

    data class SocketMetadata(val sslSocketFactory: SSLSocketFactory, val trustManager: X509TrustManager)

    private fun createMetadata(): SocketMetadata {

        //android system ssl(openssl)
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).also {
            it.init(null as KeyStore)
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