@file:JvmName("RetrofitHelper")

package com.electrolytej.main.network

import com.electrolytej.network.Okhttper
import com.electrolytej.network.adapter.ObservableOrMainCallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.OkHttpCallFactory
import retrofit2.Retrofit

object Retrofiter {
    private const val BASE_URL = "http://localhost:50195"
    private val retrofit: Retrofit

    init {
        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL)
                .callFactory(OkHttpCallFactory.create(Okhttper.create(com.electrolytej.main.network.URLInterceptor()).okHttpClient))
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(
                    ObservableOrMainCallAdapterFactory(
                        AndroidSchedulers.mainThread()
                    )
                )
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addConverterFactory(ProtoConverterFactory.create())
//            .addConverterFactory(WireConverterFactory.create())
                .build()

    }

    @JvmStatic
    fun <T> createApi(service: Class<T>): T = retrofit.create(service)
}