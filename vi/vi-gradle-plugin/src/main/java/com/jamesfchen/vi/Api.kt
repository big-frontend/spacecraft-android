package com.jamesfchen.vi

import okhttp3.*
import java.io.IOException

object Api {
    private val sOkHttpClient: OkHttpClient = OkHttpClient()

    fun postForm(url: String, multipartBody: MultipartBody) {
        val r: Request = Request.Builder().url(url).post(multipartBody).build()
        println(r)
        try {
            sOkHttpClient.newCall(r).execute().use { resp -> println(resp)
                println(resp.body()?.string())
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}