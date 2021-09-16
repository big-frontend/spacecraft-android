package com.jamesfchen.loader.monitor

import android.app.Activity
import android.util.Log
import android.view.Choreographer
import android.view.ViewTreeObserver
import jamesfchen.widget.getViewOffsetHelper

/**
 * ui没有更新监听回调不断，有性能损耗
 */
class FrameTrace1 : Choreographer.FrameCallback, ViewTreeObserver.OnDrawListener {
    companion object {
        private val myFrameCallback = FrameTrace1()
        fun start(activity: Activity) {
            Choreographer.getInstance().postFrameCallback(myFrameCallback)
            activity.window.decorView.viewTreeObserver.addOnDrawListener(myFrameCallback)
        }

        fun stop(activity: Activity) {
            Choreographer.getInstance().removeFrameCallback(myFrameCallback)
            activity.window.decorView.viewTreeObserver.removeOnDrawListener(myFrameCallback)
        }
    }

    private var startTime = -1L
    private var endTime = -1L

    //有性能损耗，如果页面没有更新，vsync也会周期发送，导致doFrame不停地被调用
    override fun doFrame(frameTimeNanos: Long) {
        if (isDrawing) {
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
        }
        Choreographer.getInstance().postFrameCallback(this)
        isDrawing = false
    }

    private var isDrawing = false
    override fun onDraw() {
        isDrawing = true

    }
}
