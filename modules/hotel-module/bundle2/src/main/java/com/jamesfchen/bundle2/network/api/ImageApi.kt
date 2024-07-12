package com.jamesfchen.bundle2.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


fun getImageApi(): ImageApi {
    return Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ImageApi::class.java)
}
interface ImageApi {
    @GET("/galley/list")
    suspend fun getImages():List<String>

}