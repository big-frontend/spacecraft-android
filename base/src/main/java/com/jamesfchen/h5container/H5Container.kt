package com.jamesfchen.h5container

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import com.jamesfchen.base.R


class H5Container @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(
    context,
    attrs,
    defStyleAttr
) {
    private val root: View by lazy {
        return@lazy View.inflate(getContext(), R.layout.view_h5container, null)
    }

}
