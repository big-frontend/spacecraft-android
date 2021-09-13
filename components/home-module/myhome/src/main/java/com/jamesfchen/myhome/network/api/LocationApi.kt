package com.jamesfchen.myhome.network.api

import com.jamesfchen.myhome.BuildConfig
import com.jamesfchen.myhome.model.L7
import com.jamesfchen.myhome.network.Httper
import okhttp3.CacheControl
import okhttp3.Request
import okhttp3.Response

object LocationApi {
    //location/datas
    suspend fun getDatas(): List<L7> {
        val r = Request.Builder().url("${BuildConfig.CONFIG_SERVER_HOST}/location/datas")
//            .cacheControl(CacheControl.FORCE_NETWORK)
            .get()
            .build()
        return Httper.getInstance().sendRequest(r)
    }
}