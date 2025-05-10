package com.electrolytej.bundle2.network

import com.electrolytej.bundle2.page.workmanager.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {
    companion object {
        // Provide your own clientId to test Imgur uploads.
        const val IMGUR_CLIENT_ID = ""
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = request.headers.newBuilder()
            .add("Authorization", "Client-ID " + IMGUR_CLIENT_ID).build()
        val authenticatedRequest = request.newBuilder().headers(headers).build()
        return chain.proceed(authenticatedRequest)
    }
}