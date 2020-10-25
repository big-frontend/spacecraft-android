package com.hawksjamesf.uicomponent.gesture

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
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false

    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    //GestureDetector.OnDoubleTapListener end
}