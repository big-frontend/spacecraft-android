package com.electrolytej.bundle2.network.api

import com.electrolytej.bundle2.network.model.PostImageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


//fun getImageApi(): ImageApi {
//    return Retrofit.Builder()
//        .baseUrl("")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ImageApi::class.java)
//}
interface ImageApi {
    @GET("/galley/list")
    suspend fun getImages():List<String>

    @Multipart
    @POST("image")
    fun postImage(@Part image: MultipartBody.Part): Call<PostImageResponse>

}