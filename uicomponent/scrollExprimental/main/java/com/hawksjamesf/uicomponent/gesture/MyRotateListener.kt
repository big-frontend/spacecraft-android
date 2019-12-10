package com.hawksjamesf.uicomponent.gesture

import com.almeros.android.multitouch.RotateGestureDetector

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/11/2019  Wed
 */
class MyRotateListener: RotateGestureDetector.SimpleOnRotateGestureListener() {
    override fun onRotate(detector: RotateGestureDetector?): Boolean {
        return super.onRotate(detector)
    }

    override fun onRotateEnd(detector: RotateGestureDetector?) {
        super.onRotateEnd(detector)
    }

    override fun onRotateBegin(detector: RotateGestureDetector?): Boolean {
        return super.onRotateBegin(detector)
    }
}