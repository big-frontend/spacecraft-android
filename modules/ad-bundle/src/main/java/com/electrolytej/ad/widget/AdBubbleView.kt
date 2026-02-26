package com.electrolytej.ad.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.drawToBitmap
import com.electrolytej.ad.R

class AdBubbleView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : FrameLayout(ctx, attrs, defStyleAttr) {

    private val root = View.inflate(ctx, R.layout.ad_view_bubble, this)
    private val ivBubble by lazy { root.findViewById<View>(R.id.iv_bubble) }

    init {
        val startValue = 1f
        val endValue = startValue + 0.1f
        val scaleXA = ObjectAnimator.ofFloat(ivBubble, "scaleX", startValue, endValue)
        scaleXA.duration = 680
        scaleXA.repeatMode = ValueAnimator.REVERSE
        scaleXA.repeatCount = ValueAnimator.INFINITE
        scaleXA.start()

        val scaleYA = ObjectAnimator.ofFloat(ivBubble, "scaleY", startValue, endValue)
        scaleYA.duration = 680
        scaleYA.repeatMode = ValueAnimator.REVERSE
        scaleYA.repeatCount = ValueAnimator.INFINITE
        scaleYA.start()
//        Glide.with(context)
//            .load(R.drawable.back)
//            .into(ivBack)
//        ivBack.setImageURI("res://drawable/" + R.drawable.back);
        ivBubble.setOnClickListener {
            Log.d("BubbleAdView", "点击响应")
        }
        ivBubble.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val bitmap = ivBubble.drawToBitmap()
                val pixel = bitmap.getPixel(event.x.toInt(), event.y.toInt())
                val argb = Integer.toHexString(pixel)
                if (pixel == 0) return@setOnTouchListener true;
            }
            return@setOnTouchListener false;
        }


    }

}