package com.jamesfchen.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver

/**
 * 无侵入的方式：改进的点，通过arthook方案，hook ImageView的 setImageBitmap方法，实现大图检测
 */
class BaseImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewTreeObserver.removeOnPreDrawListener(this)
                Log.d(
                    "baseimageview",
                    "onPreDraw w/h:${bm?.width}/${bm?.height} imageview w/h:${width}/${height}"
                )
                return false
            }

        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (drawable != null) {
            Log.d(
                "baseimageview",
                "onDraw w/h:${drawable.intrinsicWidth}/${drawable.intrinsicHeight} imageview w/h:${width}/${height}"
            )
        }
    }
}