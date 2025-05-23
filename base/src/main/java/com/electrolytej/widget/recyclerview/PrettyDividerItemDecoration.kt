package com.electrolytej.widget.recyclerview

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.electrolytej.util.dp

class PrettyDividerItemDecoration(context: Context, orientation: Int) :
    DividerItemDecoration(context, orientation) {
    var hideLastLine: Boolean = true
    private var _orientation = HORIZONTAL
    private var _Divider: Drawable? = null
    var itemLeft = 0.dp
    var itemTop = 0.dp
    var itemRight = 0.dp
    var itemBottom = 5.dp
    override fun setOrientation(orientation: Int) {
        super.setOrientation(orientation)
        this._orientation = orientation
    }

    override fun setDrawable(drawable: Drawable) {
        super.setDrawable(drawable)
        this._Divider = drawable
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        if (_Divider == null) {
            outRect[0, 0, 0] = 0
            return
        }
        if (_orientation == VERTICAL) {
            outRect[itemLeft, itemTop, itemRight] = _Divider!!.intrinsicHeight + itemBottom
        } else {
            outRect[itemLeft, itemTop, _Divider!!.intrinsicWidth + itemRight] = itemBottom
        }
        if (parent.adapter?.itemCount == null || !hideLastLine) return
        val curPosition = parent.getChildAdapterPosition(view)
        // 最后一个item不加分割线
        if (parent.layoutManager is GridLayoutManager) {
            val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
            if (curPosition >= parent.adapter!!.itemCount - spanCount) {
                outRect[0, 0, 0] = 0
            }
        } else if (parent.layoutManager is LinearLayoutManager) {
            if (curPosition == parent.adapter!!.itemCount - 1) {
                outRect[0, 0, 0] = 0
            }
        } else if (parent.layoutManager is StaggeredGridLayoutManager) {

        }

    }
}