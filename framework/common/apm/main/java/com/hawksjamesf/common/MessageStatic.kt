@file:JvmName("MessageStatic")

package com.hawksjamesf.common

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import android.util.Printer
import android.view.Choreographer

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Feb/21/2021  Sun
 */
class MessageStatic : Printer {
    companion object {
        val handle = Handler()
        val ht = object : HandlerThread("ht") {
            override fun onLooperPrepared() {
                super.onLooperPrepared()
                Choreographer.getInstance().postFrameCallback {
//                    Log.d("cjf", "${Thread.currentThread().id} postFrameCallback 开始刷新的时间戳：${it} ns")

                }
            }
        }
        private var startTime = -1L
        private var endTime = -1L

        private class MyFrameCallback : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                if (startTime == -1L) {
                    startTime = frameTimeNanos
                } else {
                    endTime = frameTimeNanos
//                    Log.d("cjf", "${Thread.currentThread().id} postFrameCallback 耗时：${(endTime - startTime)/1000} ms")
                    startTime =-1
                }
                Choreographer.getInstance().postFrameCallback(this)
            }
        }

        @JvmStatic
        fun init(ctx: Context) {
            Choreographer.getInstance().postFrameCallback(MyFrameCallback())

            ht.start()
            val msg = Message.obtain(handle, object : Runnable {
                override fun run() {

                }
            })
            handle.sendMessage(msg)
            val isAlive = true
            var count = 0
            LooperMonitor.register(object : LooperMonitor.LooperDispatchListener() {
                override fun isValid(): Boolean {
//                    Log.d("cjf", "isValid $count")
                    return isAlive
                }

                override fun dispatchStart() {
//                    Log.d("cjf", "dispatchStart $count")
                    super.dispatchStart()
                }

                override fun dispatchEnd() {
//                    Log.d("cjf", "dispatchEnd $count")
                    count += 1
                    super.dispatchEnd()
                }
            })
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Looper.getMainLooper().queue.addIdleHandler {
//                    Log.d("cjf", "addIdleHandler")
                    true
                }
            }

        }
    }

    override fun println(x: String?) {

    }

}