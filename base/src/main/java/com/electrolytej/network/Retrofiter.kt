package com.electrolytej.network

import com.electrolytej.network.adapter.ObservableOrMainCallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.OkHttpCallFactory
import retrofit2.Retrofit

open class Retrofiter(
    baseUrl: String,
    callAdapterFactories: List<CallAdapter.Factory>? = null,
    converterFactories: List<Converter.Factory>? = null,
    vararg interceptors: Interceptor,
) {
    companion object {
        @JvmStatic
        fun create(
            baseUrl: String, vararg interceptors: Interceptor
        ) = Retrofiter(
            baseUrl,
            listOf(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread())),
            interceptors = interceptors
        )
    }

    private val retrofit: Retrofit

    init {
        val builder = Retrofit.Builder().baseUrl(baseUrl)
            .callFactory(OkHttpCallFactory.create(Okhttper.create(*interceptors).okHttpClient))
        //            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
//        .addCallAdapterFactory(
//            ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread())
//        )
        callAdapterFactories?.forEach {
            builder.addCallAdapterFactory(it)
        }
        //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addConverterFactory(ProtoConverterFactory.create())
//            .addConverterFactory(WireConverterFactory.create())
        converterFactories?.forEach {
            builder.addConverterFactory(it)
        }
        retrofit = builder.build()
    }

    fun <T> createApi(service: Class<T>): T = retrofit.create(service)

}