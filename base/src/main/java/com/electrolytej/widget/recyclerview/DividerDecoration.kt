package com.electrolytej.widget.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

//class DividerDecoration : RecyclerView.ItemDecoration() {
//    private val ATTRS = intArrayOf(android.R.attr.listDivider)
//
//    private var mDivider: Drawable? = null
//    private var mInsets = 0
//
//    fun DividerDecoration(context: Context) {
//        val a = context.obtainStyledAttributes(ATTRS)
//        mDivider = a.getDrawable(0)
//        a.recycle()
//        mInsets = context.resources.getDimensionPixelSize(R.dimen.card_insets)
//    }
//
//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        drawVertical(c, parent)
//    }
//
//    /** Draw dividers underneath each child view  */
//    fun drawVertical(c: Canvas?, parent: RecyclerView) {
//        val left: Int = parent.paddingLeft
//        val right: Int = parent.width - parent.paddingRight
//        val childCount: Int = parent.childCount
//        for (i in 0 until childCount) {
//            val child: View = parent.getChildAt(i)
//            val params: RecyclerView.LayoutParams = child
//                .layoutParams as RecyclerView.LayoutParams
//            val top: Int = child.bottom + params.bottomMargin + mInsets
//            val bottom = top + mDivider!!.intrinsicHeight
//            mDivider!!.setBounds(left, top, right, bottom)
//            mDivider!!.draw(c!!)
//        }
//    }
//
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        //We can supply forced insets for each item view here in the Rect
//        outRect[mInsets, mInsets, mInsets] = mInsets
//    }
//}

class DividerDecoration(context: Context, orientation: Int) :
    DividerItemDecoration(context, orientation) {
    var hideLastLine: Boolean = true

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 最后一个item不加分割线
        if (parent.adapter?.itemCount != null
            && hideLastLine
            && parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1
        ) {
            outRect[0, 0, 0] = 0
        }
    }
}