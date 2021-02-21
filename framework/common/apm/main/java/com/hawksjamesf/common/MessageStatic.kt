@file:JvmName("MessageStatic")

package com.hawksjamesf.common

import android.content.Context
import android.os.Looper
import android.util.Log
import android.util.Printer

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Feb/21/2021  Sun
 */
class MessageStatic : Printer {
    companion object {
        @JvmStatic
        fun init(ctx: Context) {
            var start = 0L
            Looper.getMainLooper().setMessageLogging {
                if (it?.contains(">>>>> Dispatching to") == true) {
                    start = System.currentTimeMillis()
                }
                if (it?.contains("<<<<< Finished to") == true) {
                    if (start != 0L) {
                        Log.d("cjf", "message 分发耗时：${System.currentTimeMillis() - start} ms")
                    }
                }
            }
        }
    }

    override fun println(x: String?) {

    }

}