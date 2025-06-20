package com.electrolytej.main.network

import com.electrolytej.network.Okhttper
import com.electrolytej.network.Retrofiter
import com.electrolytej.network.adapter.ObservableOrMainCallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.OkHttpCallFactory
import retrofit2.Retrofit

object MainRetrofiter {

    private const val BASE_URL = "http://localhost:50195"

    @JvmStatic
    fun <T> createApi(service: Class<T>): T = Retrofiter.create(
        BASE_URL
    ).createApi(service)
}