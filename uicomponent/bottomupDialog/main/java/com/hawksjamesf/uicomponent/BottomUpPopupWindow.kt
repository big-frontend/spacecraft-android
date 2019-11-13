package com.hawksjamesf.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.PopupWindow

class BottomUpPopupWindow : PopupWindow {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor() : super()
    constructor(contentView: View?) : super(contentView)
    constructor(width: Int, height: Int) : super(width, height)
    constructor(contentView: View?, width: Int, height: Int) : super(contentView, width, height)
    constructor(contentView: View?, width: Int, height: Int, focusable: Boolean) : super(contentView, width, height, focusable)
    init {

    }
}