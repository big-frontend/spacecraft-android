package com.jamesfchen.uicomponent

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.google.android.material.R

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/23/2019  Sat
 */
fun View.getViewOffsetHelper(): ViewOffsetHelper {
    var offsetHelper = getTag(R.id.view_offset_helper) as? ViewOffsetHelper
    if (offsetHelper == null) {
        offsetHelper = ViewOffsetHelper(this)
        setTag(R.id.view_offset_helper, offsetHelper)
    }
    return offsetHelper
}

fun View.getMaxOffsetForPinChild(containerHeight:Int): Int {
    val lp = layoutParams as ViewGroup.MarginLayoutParams
    return containerHeight - getViewOffsetHelper().layoutTop - height - lp.bottomMargin
}
class ViewOffsetHelper(private val view: View) {
    var layoutTop = 0
    var layoutLeft = 0
    private var topAndBottomOffset = 0
    private var leftAndRightOffset = 0
    var isVerticalOffsetEnabled = true
    var isHorizontalOffsetEnabled = true

    fun onViewLayout() { // Grab the original top and left
        layoutTop = view.top
        layoutLeft = view.left
    }

    fun applyOffsets() {
        ViewCompat.offsetTopAndBottom(view, topAndBottomOffset - (view.top - layoutTop))
        ViewCompat.offsetLeftAndRight(view, leftAndRightOffset - (view.left - layoutLeft))
    }

    fun setTopAndBottomOffset(offset: Int): Boolean {
        if (isVerticalOffsetEnabled && topAndBottomOffset != offset) {
            topAndBottomOffset = offset
            applyOffsets()
            return true
        }
        return false
    }

    fun setLeftAndRightOffset(offset: Int): Boolean {
        if (isHorizontalOffsetEnabled && leftAndRightOffset != offset) {
            leftAndRightOffset = offset
            applyOffsets()
            return true
        }
        return false
    }

}