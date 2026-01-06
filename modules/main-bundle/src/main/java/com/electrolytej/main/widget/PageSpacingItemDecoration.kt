package com.electrolytej.main.widget

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PageSpacingItemDecoration(private val spacingDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // 将dp转为px，适配不同屏幕
        val spacingPx = (spacingDp * view.resources.displayMetrics.density).toInt()

        // 获取当前页面的位置
        val position = parent.getChildAdapterPosition(view)
        // 获取总页数
        val itemCount = state.itemCount
        Log.d("cjf", "getItemOffsets ${position % 3}")
        when (position % 3) {
            0 -> {
                outRect.right = spacingPx
            }

            1 -> {
                outRect.left = spacingPx / 2
                outRect.right = spacingPx / 2
            }

            2 -> {
                outRect.right = 0
            }
        }
        // 设置间距：仅在页面之间添加横向间距（左右间距可根据需求调整）
//        when {
//            // 第一个页面：只设置右侧间距
//            position == 0 -> {
//                outRect.right = spacingPx
//            }
//            // 最后一个页面：只设置左侧间距
//            position == itemCount - 1 -> {
//                outRect.left = spacingPx
//            }
//            // 中间页面：左右都设置间距（均分总间距，避免整体偏移）
//            else -> {
//                outRect.left = spacingPx / 2
//                outRect.right = spacingPx / 2
//            }
//        }

        // 如果是垂直方向的ViewPager2，修改top/bottom即可
        // outRect.top = spacingPx
        // outRect.bottom = spacingPx
    }
}