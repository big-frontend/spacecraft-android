package com.electrolytej.ad.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.core.view.drawToBitmap

class ColorClickableImageView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : androidx.appcompat.widget.AppCompatImageView(ctx, attrs, defStyleAttr) {
    companion object {
        private const val TAG = "ColorClickableImageView"
    }

    private var color: Int? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val bitmap = drawToBitmap()
            val pixel = bitmap.getPixel(event.x.toInt(), event.y.toInt())
//            val argb = Integer.toHexString(pixel)
            if (color != null && color == pixel) {
                Log.d(TAG, "color:${color}")
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    fun blockColor(color: Int) {
        this.color = color
    }

}