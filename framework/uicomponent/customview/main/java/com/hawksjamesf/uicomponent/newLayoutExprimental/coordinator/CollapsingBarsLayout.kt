package com.hawksjamesf.uicomponent.coordinator

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.FloatRange
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.AppBarLayout
import com.hawksjamesf.uicomponent.R
import com.hawksjamesf.uicomponent.newLayoutExprimental.coordinator.getMaxOffsetForPinChild
import com.hawksjamesf.uicomponent.newLayoutExprimental.coordinator.getViewOffsetHelper
import kotlin.math.roundToInt

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/23/2019  Sat
 */
class CollapsingBarsLayout : RelativeLayout {
    companion object {
        const val TAG = "CollapsingBarsLayout"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        Log.d(TAG, "init")
//        orientation = LinearLayout.VERTICAL
    }

    var onOffsetChangedListener: OffsetUpdateListener? = null
    var currentOffset = 0
    var lastInsets: WindowInsetsCompat? = null
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Add an OnOffsetChangedListener if possible
        val parent = parent
        if (parent is AppBarLayout) { // Copy over from the ABL whether we should fit system windows
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((parent as View)))
            if (onOffsetChangedListener == null) {
                onOffsetChangedListener = OffsetUpdateListener()
            }
            parent.addOnOffsetChangedListener(onOffsetChangedListener)
            // We're attached, so lets request an inset dispatch
            ViewCompat.requestApplyInsets(this)
        }
    }

    override fun onDetachedFromWindow() { // Remove our OnOffsetChangedListener if possible and it exists
        val parent = parent
        if (onOffsetChangedListener != null && parent is AppBarLayout) {
            parent.removeOnOffsetChangedListener(onOffsetChangedListener)
        }
        super.onDetachedFromWindow()
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams() = LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

    override fun generateLayoutParams(attrs: AttributeSet?) = LayoutParams(context, attrs)

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) = LayoutParams(lp)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        Log.d(TAG, "onLayout:$changed $l $t $r $b")
        for (index in 0 until childCount) {
            getChildAt(index).getViewOffsetHelper().onViewLayout();
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw")
    }


    fun updateScrimVisibility() {

    }

    inner class OffsetUpdateListener : AppBarLayout.OnOffsetChangedListener {
        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            currentOffset = verticalOffset
            val insetTop = lastInsets?.systemWindowInsetTop ?: 0
            for (index in 0 until childCount) {
                val child = getChildAt(index)
                val layoutParams = child.layoutParams as LayoutParams
                val offsetHelper = child.getViewOffsetHelper()
                Log.d(TAG, "onOffsetChanged$verticalOffset ${layoutParams.collapseMode}")
                when (layoutParams.collapseMode) {
                    LayoutParams.COLLAPSE_MODE_OFF -> {

                    }
                    LayoutParams.COLLAPSE_MODE_PIN -> {
                        offsetHelper.setTopAndBottomOffset(MathUtils.clamp(-verticalOffset, 0, child.getMaxOffsetForPinChild(height)))

                    }
                    LayoutParams.COLLAPSE_MODE_PARALLAX -> {
                        offsetHelper.setTopAndBottomOffset((-verticalOffset * layoutParams.parallaxMultiplier).roundToInt())

                    }
                }
            }
            // Show or hide the scrims if needed
//                updateScrimVisibility()
//            this@CollapsingBarsLayout.requestLayout()
        }

    }

    class LayoutParams : RelativeLayout.LayoutParams {

        companion object {
            const val COLLAPSE_MODE_OFF = 0
            const val COLLAPSE_MODE_PIN = 1
            const val COLLAPSE_MODE_PARALLAX = 2
            const val DEFAULT_PARALLAX_MULTIPLIER = 0.5f
        }

        var collapseMode: Int = COLLAPSE_MODE_OFF
        @FloatRange(from = 0.0, to = 1.0)
        var parallaxMultiplier: Float = DEFAULT_PARALLAX_MULTIPLIER

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            val a: TypedArray = c.obtainStyledAttributes(attrs, R.styleable.CollapsingBarsLayout_Layout)
            collapseMode = a.getInt(R.styleable.CollapsingBarsLayout_Layout_cbl_layout_collapseMode, COLLAPSE_MODE_OFF)
            parallaxMultiplier = a.getFloat(R.styleable.CollapsingBarsLayout_Layout_cbl_layout_collapseParallaxMultiplier, DEFAULT_PARALLAX_MULTIPLIER)
            a.recycle()
        }

        constructor(width: Int, height: Int) : super(width, height)
        //        constructor(width: Int, height: Int, weight: Float) : super(width, height, weight)
        constructor(p: ViewGroup.LayoutParams?) : super(p)

        constructor(source: ViewGroup.MarginLayoutParams?) : super(source)
        constructor(source: LinearLayout.LayoutParams?) : super(source)

        init {

        }

    }

}