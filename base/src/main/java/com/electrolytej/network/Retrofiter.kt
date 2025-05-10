@file:JvmName("RetrofitHelper")

package com.electrolytej.network

import com.electrolytej.network.adapter.ObservableOrMainCallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Interceptor
import retrofit2.OkHttpCallFactory
import retrofit2.Retrofit

open class Retrofiter(
    baseUrl: String, vararg interceptors: Interceptor
) {
    companion object {
        @JvmStatic
        fun create(
            baseUrl: String, vararg interceptors: Interceptor
        ) = Retrofiter(baseUrl, *interceptors)
    }

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .callFactory(OkHttpCallFactory.create(Okhttper.create(*interceptors).okHttpClient))
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

    fun <T> createApi(service: Class<T>): T = retrofit.create(service)

}