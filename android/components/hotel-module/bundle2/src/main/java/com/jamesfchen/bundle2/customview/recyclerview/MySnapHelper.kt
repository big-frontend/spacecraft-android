package com.jamesfchen.bundle2.customview.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

/**
const val SNAP_MODE_NONE=0 //RecyclerView 默认的滑动效果
const val SNAP_MODE_PAGE=1//像Viewpager一样一次只滑动一页
const val SNAP_MODE_LINEAR=2//最中间的元素居中
 */
class MySnapHelper : SnapHelper() {
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        return null
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return null
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        return 0
    }
}