package com.electrolytej.ad.util

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

object SnackBarUtil {
    fun show(view: View, msg: String) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        // 获取Snackbar的TextView
        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        // 设置多行显示
        textView.maxLines = 5 // 设置最大行数
        // 对于长文本自动换行
        textView.isSingleLine = false
        snackbar.show()
    }
}