package com.electrolytej.widget.gesture

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/11/2019  Wed
 */
class MyDoubleTapListener : GestureDetector.OnDoubleTapListener {
    //GestureDetector.OnDoubleTapListener start
    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d("cjf","onDoubleTap action:${e?.action}")
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.d("cjf","onDoubleTapEvent action:${e?.action}")
        return false

    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d("cjf","onSingleTapConfirmed action:${e?.action}")
        return false
    }

    //GestureDetector.OnDoubleTapListener end
}