package com.jamesfchen.bundle2.page.customview.gesture

import android.util.Log
import com.almeros.android.multitouch.RotateGestureDetector

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/11/2019  Wed
 */
class MyRotateListener: RotateGestureDetector.SimpleOnRotateGestureListener() {
    override fun onRotateBegin(detector: RotateGestureDetector): Boolean {
        Log.d("cjf",">>>onRotateBegin")
        return super.onRotateBegin(detector)
    }
    override fun onRotateEnd(detector: RotateGestureDetector) {
        Log.d("cjf","<<<onRotateEnd")
        super.onRotateEnd(detector)
    }
    override fun onRotate(detector: RotateGestureDetector): Boolean {
        Log.d("cjf","onRotate")
        return super.onRotate(detector)
    }
}