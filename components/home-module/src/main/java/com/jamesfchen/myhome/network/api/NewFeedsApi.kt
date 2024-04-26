package com.jamesfchen.myhome.network.api

import com.jamesfchen.myhome.network.Retrofiter


interface NewFeedsApi {
    companion object {
        fun create(): NewFeedsApi {
            return Retrofiter.createApi(NewFeedsApi::class.java)
        }
    }
}