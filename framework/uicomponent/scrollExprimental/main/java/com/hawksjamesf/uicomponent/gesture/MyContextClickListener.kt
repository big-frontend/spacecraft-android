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
class MyContextClickListener: GestureDetector.OnContextClickListener  {
    //GestureDetector.OnContextClickListener start
    override fun onContextClick(e: MotionEvent?): Boolean {
        return false
    }

    //GestureDetector.OnContextClickListener end
}