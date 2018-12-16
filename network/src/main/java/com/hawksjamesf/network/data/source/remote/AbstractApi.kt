package com.hawksjamesf.network.data.source.remote

import com.hawksjamesf.network.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
abstract class AbstractApi<T:Any> {
    protected abstract var api: T

    init {
        api = Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_URL)
                //                .baseUrl("http://localhost:50195")
                .client(OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(com.hawksjamesf.network.data.source.remote.URLInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(com.hawksjamesf.network.data.source.remote.ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(getClass().java)
    }

    abstract fun getClass(): KClass<T>
}