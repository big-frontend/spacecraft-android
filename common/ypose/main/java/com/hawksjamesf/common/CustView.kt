package com.hawksjamesf.common

import android.content.Context
import android.util.AttributeSet

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/05/2020  Sun
 */
class CustView: BaseCustView2,ICustView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    companion object{
        private const val custTag="default"
    }
}