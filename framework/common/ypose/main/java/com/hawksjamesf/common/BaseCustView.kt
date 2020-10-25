package com.hawksjamesf.common

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewFlipper

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/05/2020  Sun
 */
open class BaseCustView : ViewFlipper {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    private  val basecustTag="default"
    companion object{
    }
}