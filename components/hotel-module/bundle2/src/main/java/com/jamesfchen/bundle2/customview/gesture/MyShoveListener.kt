package com.jamesfchen.bundle2.customview.gesture

import android.util.Log
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
        Log.d("cjf","onShove")
        return super.onShove(detector)
    }

    override fun onShoveBegin(detector: ShoveGestureDetector?): Boolean {
        Log.d("cjf",">>>onShoveBegin")
        return super.onShoveBegin(detector)
    }

    override fun onShoveEnd(detector: ShoveGestureDetector?) {
        Log.d("cjf","<<<onShoveEnd")
        super.onShoveEnd(detector)
    }
}