package com.hawksjamesf.network.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/25/2018  Thu
 */
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        return null
    }
}