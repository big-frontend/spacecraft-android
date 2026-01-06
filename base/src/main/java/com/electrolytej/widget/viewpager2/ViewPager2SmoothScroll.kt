package com.electrolytej.widget.viewpager2

import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * 让 ViewPager2 松手后的吸附滚动更“丝滑”。
 *
 * 用法：
 * viewPager2.setPagerSmoothScrollSpeedFactor(1.3f)
 *  - factor > 1: 更慢更丝滑
 *  - factor < 1: 更快
 */
fun androidx.viewpager2.widget.ViewPager2.setPagerSmoothScrollSpeedFactor(factor: Float) {
    val rv = getChildAt(0) as? RecyclerView ?: return
    val currentLm = rv.layoutManager as? LinearLayoutManager ?: return

    val baseMillisPerInch = 25f
    val speedFactor = factor.coerceAtLeast(0.1f)

    rv.layoutManager = object : LinearLayoutManager(context, currentLm.orientation, false) {
        override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
            val scroller = object : LinearSmoothScroller(recyclerView.context) {
                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    // ms/px，越大越慢越丝滑
                    return (baseMillisPerInch * speedFactor) / displayMetrics.densityDpi
                }

                override fun calculateTimeForDeceleration(dx: Int): Int {
                    // 略微增加减速时间，让尾段更柔和
                    return (super.calculateTimeForDeceleration(dx) * 1.1f).toInt()
                }
            }
            scroller.targetPosition = position
            startSmoothScroll(scroller)
        }
    }
}
