package com.electrolytej.bundle2.page.customview.gesture

import android.util.Log
import android.view.ScaleGestureDetector

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/11/2019  Wed
 */
class MyScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        Log.d("cjf",">>>onScaleBegin")
        return super.onScaleBegin(detector)
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        Log.d("cjf","<<<onScaleEnd")
        super.onScaleEnd(detector)
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        Log.d("cjf","onScale")
        return super.onScale(detector)
    }
}