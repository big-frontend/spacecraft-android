package com.hawksjamesf.uicomponent.gesture

import com.almeros.android.multitouch.ShoveGestureDetector

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/11/2019  Wed
 */
class MyShoveListener:ShoveGestureDetector.SimpleOnShoveGestureListener() {
    override fun onShove(detector: ShoveGestureDetector?): Boolean {
        return super.onShove(detector)
    }

    override fun onShoveBegin(detector: ShoveGestureDetector?): Boolean {
        return super.onShoveBegin(detector)
    }

    override fun onShoveEnd(detector: ShoveGestureDetector?) {
        super.onShoveEnd(detector)
    }
}