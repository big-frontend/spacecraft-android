package com.hawksjamesf.uicomponent.coordinator

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.hawksjamesf.uicomponent.R

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/23/2019  Sat
 */
class CollapsingBarsLayoutLayoutParams : RelativeLayout.LayoutParams {

    companion object {
        const val COLLAPSE_MODE_OFF = 0
        const val COLLAPSE_MODE_PIN = 1
        const val COLLAPSE_MODE_PARALLAX = 2
        const val DEFAULT_PARALLAX_MULTIPLIER = 0.5f
    }

    var collapseMode: Int = COLLAPSE_MODE_OFF
    var parallaxMultiplier: Float = DEFAULT_PARALLAX_MULTIPLIER

    constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
        val a: TypedArray = c.obtainStyledAttributes(attrs, R.styleable.CollapsingBarsLayout_Layout)
        collapseMode = a.getInt(R.styleable.CollapsingBarsLayout_Layout_hotel_layout_collapseMode, COLLAPSE_MODE_OFF)
        parallaxMultiplier = a.getFloat(R.styleable.CollapsingBarsLayout_Layout_hotel_layout_collapseParallaxMultiplier, DEFAULT_PARALLAX_MULTIPLIER)
        a.recycle()
    }

    constructor(width: Int, height: Int) : super(width, height)
    //    constructor(width: Int, height: Int, weight: Float) : super(width, height, weight)
    constructor(p: ViewGroup.LayoutParams?) : super(p)

    constructor(source: ViewGroup.MarginLayoutParams?) : super(source)
    constructor(source: LinearLayout.LayoutParams?) : super(source)

    init {

    }

}