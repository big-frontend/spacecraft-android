package com.electrolytej.bundle2.page.customview.gesture

import android.util.Log
import com.electrolytej.widget.multitouch.ShoveGestureDetector


class MyShoveListener: ShoveGestureDetector.SimpleOnShoveGestureListener() {
    override fun onShove(detector: ShoveGestureDetector): Boolean {
        Log.d("cjf","onShove")
        return super.onShove(detector)
    }

    override fun onShoveBegin(detector: ShoveGestureDetector): Boolean {
        Log.d("cjf",">>>onShoveBegin")
        return super.onShoveBegin(detector)
    }

    override fun onShoveEnd(detector: ShoveGestureDetector) {
        Log.d("cjf","<<<onShoveEnd")
        super.onShoveEnd(detector)
    }
}