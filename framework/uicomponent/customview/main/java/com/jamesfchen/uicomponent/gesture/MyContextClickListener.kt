package com.jamesfchen.uicomponent.gesture

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
class MyContextClickListener: GestureDetector.OnContextClickListener  {
    //GestureDetector.OnContextClickListener start
    override fun onContextClick(e: MotionEvent?): Boolean {
        Log.d("cjf","onContextClick action:${e?.action}")
        return false
    }

    //GestureDetector.OnContextClickListener end
}