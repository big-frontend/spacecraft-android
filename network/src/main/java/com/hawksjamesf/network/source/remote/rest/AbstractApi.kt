package com.hawksjamesf.network.source.remote.rest

import com.hawksjamesf.network.BuildConfig
import com.hawksjamesf.network.source.remote.rest.weather.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import retrofit2.converter.wire.WireConverterFactory
import java.util.concurrent.TimeUnit
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
        api = Retrofit.Builder()
                .baseUrl(baseUrl)
                //                .baseUrl("http://localhost:50195")
                .client(OkHttpClient.Builder()
//                        .sslSocketFactory()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(URLInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(ProtoConverterFactory.create())
                .addConverterFactory(WireConverterFactory.create())
                .build()
                .create(getClass().java)
    }

    abstract fun getClass(): KClass<T>
}