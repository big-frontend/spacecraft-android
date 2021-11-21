@file:JvmName("ViewUtil")

package jamesfchen.widget

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jamesfchen.loader.R

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

fun View.getMaxOffsetForPinChild(containerHeight: Int): Int {
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

private fun isVScrollable(viewgroup: ViewGroup, view: View?): Boolean {
    return if (view != null) {
        viewgroup.height < view.height + viewgroup.paddingTop + viewgroup.paddingBottom
    } else false
}

private fun isHScrollable(viewgroup: ViewGroup, view: View?): Boolean {
    return if (view != null) {
        viewgroup.width < view.width + viewgroup.paddingLeft + viewgroup.paddingRight
    } else false
}

/*
     * Negative to check scrolling up, positive to check scrolling down.
     */
private fun canScroll(
    v: View,
    checkV: Boolean,
    delta: Int,
    x: Int,
    y: Int,
    orientation: Int
): Boolean {
    if (v is ViewGroup) {
        val group = v
        val scrollX = v.getScrollX()
        val scrollY = v.getScrollY()
        val count = group.childCount
        for (i in count - 1 downTo 0) {
            val child = group.getChildAt(i)
            if (child.left <= x + scrollX && x + scrollX < child.right && child.top <= y + scrollY && y + scrollY < child.bottom //判断点击是否位于可滑动的区域
                && canScroll(
                    child,
                    true,
                    delta,
                    x + scrollX - child.left,
                    y + scrollY - child.top,
                    orientation
                )
            ) {
                return true
            }
        }
    }
    return if (orientation == RecyclerView.HORIZONTAL) {
        checkV && v.canScrollHorizontally(delta)
    } else {
        checkV && v.canScrollVertically(delta)
    }
}