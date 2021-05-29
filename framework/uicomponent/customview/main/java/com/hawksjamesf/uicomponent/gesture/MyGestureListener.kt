package com.hawksjamesf.uicomponent.gesture

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
class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
    // GestureDetector.OnGestureListener end
    override fun onShowPress(e: MotionEvent?) {
        Log.d("cjf","onShowPress action:${e?.action}")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d("cjf","onSingleTapUp action:${e?.action}")
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d("cjf","onDown action:${e?.action}")
        return false
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.d("cjf","onFling action:${e1?.action} ${e2?.action}")
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        Log.d("cjf","onScroll action:${e1?.action} ${e2?.action}")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.d("cjf","onLongPress action:${e?.action}")
    }

    // GestureDetector.OnGestureListener end
}