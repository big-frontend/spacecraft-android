package com.jamesfchen.loader.monitor

import android.util.Log
import android.view.Choreographer

/**
 * ui没有更新监听回调不断，有性能损耗
 */
class FrameTrace1 : Choreographer.FrameCallback {
    companion object {
        private val myFrameCallback = FrameTrace1()
        fun start() {
            Choreographer.getInstance().postFrameCallback(myFrameCallback)
        }

        fun stop() {
            Choreographer.getInstance().removeFrameCallback(myFrameCallback)
        }
    }

    private var startTime = -1L
    private var endTime = -1L

    //有性能损耗，如果页面没有更新，vsync也会周期发送，导致doFrame不停地被调用
    override fun doFrame(frameTimeNanos: Long) {
        if (startTime == -1L) {
            startTime = frameTimeNanos
        } else {
            endTime = frameTimeNanos
            Log.d(
                TAG_APM_MONITOR,
                "${Thread.currentThread().id} postFrameCallback 耗时：${(endTime - startTime) / 1000} ms"
            )
            startTime = -1
        }
        Choreographer.getInstance().postFrameCallback(this)
    }
}
