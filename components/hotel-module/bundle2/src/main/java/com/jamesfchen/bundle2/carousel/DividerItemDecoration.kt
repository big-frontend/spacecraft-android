package com.jamesfchen.bundle2.carousel

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/30/2018  Tue
 */
class DividerItemDecoration(
        private val mContext: Context,
        private val mOrientation: Int
) : RecyclerView.ItemDecoration() {
    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }

    private val mDivider: Drawable?
    private var mBounds = Rect()//包含margin和offset

    init {
        val typedArray = mContext.obtainStyledAttributes(ATTRS)
        mDivider = typedArray.getDrawable(0)
        typedArray.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }

        if (mOrientation == VERTICAL) {
            drawVertical(c, parent, state)
        } else {
            drawHorizontal(c, parent, state)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()
        val left: Int
        val right: Int
        //android:clipToPadding="false" View/ViewGroup的绘制区域可以穿过Container限制的padding区域。clip children to their bound
        //android:clipChildren="false" 当执行动画是，View/ViewGroup可以穿过Container之前限制的区域。clip children to the padding of the group
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            //DIFFERENCE是第一次不同于第二次的部分显示出来
            //REPLACE是显示第二次的
            //REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示
            //INTERSECT交集显示 默认选项
            //UNION全部显示
            //XOR补集 就是全集的减去交集生育部分显示
            c.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }
        parent.children.forEach {
            parent.layoutManager?.getDecoratedBoundsWithMargins(it, mBounds)
            val bottom = mBounds.bottom + Math.round(it.translationY)
            val top = bottom - mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
//        for (i in 0 until childCount) {
//            val child = parent.getChildAt(i)
//            parent.getDecoratedBoundsWithMargins(child, mBounds)
//            val bottom = mBounds.bottom + Math.round(child.translationY)
//            val top = bottom - mDivider?.intrinsicHeight!!
//            mDivider.setBounds(left, top, right, bottom)
//            mDivider.draw(c)
//        }
        c.restore()

    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            c.clipRect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }
        parent.children.forEach {
            parent.layoutManager?.getDecoratedBoundsWithMargins(it,mBounds)
            val right=mBounds.right+Math.round(it.translationX)
            val left=right-mDivider!!.intrinsicWidth
            mDivider.setBounds(left,top,right,bottom)
            mDivider.draw(c)
        }
        c.restore()

    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (mOrientation == VERTICAL) {
//            Set the rectangle's coordinates to the specified values
//   draw vertical:||
            //     ||
            //     ||
            //     ||
            //     ||
            //     ||
            //     ||
            //     ||
            outRect.set(0, 0, 0, mDivider.intrinsicHeight)
        } else {
            // draw horizontal:===========
            outRect.set(0, 0, mDivider.intrinsicWidth, 0)
        }
    }
}