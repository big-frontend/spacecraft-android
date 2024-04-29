package com.electrolytej.feeds.network.api

import com.electrolytej.feeds.network.Retrofiter

interface NewFeedsApi {
    companion object {
        fun create(): NewFeedsApi {
            return Retrofiter.createApi(NewFeedsApi::class.java)
        }
    }
}