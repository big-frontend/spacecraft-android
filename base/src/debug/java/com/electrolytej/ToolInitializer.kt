package com.electrolytej

import android.content.Context
import androidx.startup.Initializer

class ToolInitializer : Initializer<Unit> {
    override fun create(context: Context) {
//        DoKit.Builder(this)
//            .productId("需要使用平台功能的话，需要到dokit.cn平台申请id")
//            .build()

    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}