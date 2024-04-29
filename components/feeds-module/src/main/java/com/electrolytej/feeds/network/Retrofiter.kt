@file:JvmName("RetrofitHelper")

package com.electrolytej.feeds.network

import com.jamesfchen.network.Okhttper
import com.jamesfchen.network.adapter.ObservableOrMainCallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.OkHttpCallFactory
import retrofit2.Retrofit

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 五月/21/2021  星期五
 */
object Retrofiter {
    private const val BASE_URL = "http://localhost:50195"
    private val retrofit: Retrofit

    init {
        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL)
                .callFactory(OkHttpCallFactory.create(Okhttper.create().okHttpClient))
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
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