package com.jamesfchen.login.network

import com.electrolytej.network.Okhttper
import com.electrolytej.network.adapter.CoroutineCallAdapterFactory
import com.electrolytej.network.adapter.ObservableOrMainCallAdapterFactory

import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.OkHttpCallFactory
import retrofit2.Retrofit
import kotlin.reflect.KClass

object Retrofiter {
    private const val BASE_URL = "https://www.easy-mock.com/mock/5ce10c941e436e14aceef040/"
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .callFactory(OkHttpCallFactory.create(Okhttper.create().okHttpClient))
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
//            .addCallAdapterFactory(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
            //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addConverterFactory(ProtoConverterFactory.create())
//            .addConverterFactory(WireConverterFactory.create())
            .build()

    }

    @JvmStatic
    fun <T : Any> createApi(service: KClass<T>): T = retrofit.create(service.java)
}