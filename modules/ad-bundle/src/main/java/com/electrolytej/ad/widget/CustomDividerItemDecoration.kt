package com.electrolytej.ad.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(private val dividerHeight: Int, color: Int) :
    RecyclerView.ItemDecoration() {
    private val dividerPaint = Paint()

    init {
        dividerPaint.color = color
        dividerPaint.style = Paint.Style.FILL
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 最后一个item不加分割线
//            if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
        outRect.bottom = dividerHeight
//            }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val top = child.bottom
            val bottom = top + dividerHeight
            canvas.drawRect(
                left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint
            )
        }
    }
}