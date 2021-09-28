package com.jamesfchen.main

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver

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
                    "final bitmap w/h:${bm?.width}/${bm?.height} imageview w/h:${width}/${height}"
                )
                return false
            }

        })
    }
}