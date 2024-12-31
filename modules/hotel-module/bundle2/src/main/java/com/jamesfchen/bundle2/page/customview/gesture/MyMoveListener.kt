package com.jamesfchen.bundle2.page.customview.gesture

import android.util.Log
import com.electrolytej.widget.multitouch.MoveGestureDetector

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/11/2019  Wed
 */
class MyMoveListener : MoveGestureDetector.SimpleOnMoveGestureListener() {
    override fun onMoveBegin(detector: MoveGestureDetector): Boolean {
        Log.d("cjf", ">>>onMoveBegin")
        return super.onMoveBegin(detector)
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {
        Log.d("cjf", "<<<onMoveEnd")
        super.onMoveEnd(detector)
    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
        Log.d("cjf", "onMove")
        return super.onMove(detector)
    }


}