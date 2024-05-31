package com.electrolytej.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: May/01/2024  Wed
 */
class CryptoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}