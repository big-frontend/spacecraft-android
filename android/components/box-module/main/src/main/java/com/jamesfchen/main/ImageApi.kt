package com.jamesfchen.main

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


fun getImageApi():ImageApi{
    return Retrofit.Builder()
        .baseUrl(BuildConfig.IMAGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ImageApi::class.java)
}
interface ImageApi {
    @GET("/galley/list")
    suspend fun getImages():List<String>

}