package com.electrolytej.ad

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import com.electrolytej.ad.R


class ShinyAdView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : FrameLayout(ctx, attrs, defStyleAttr) {
    private val root = View.inflate(ctx, R.layout.view_shiny_ad, this)
    private val viewColor by lazy { root.findViewById<View>(R.id.view_color) }
    private val ivScene2 by lazy { root.findViewById<ImageView>(R.id.iv_scene2) }
    private val ivScene3 by lazy { root.findViewById<ImageView>(R.id.iv_scene3) }
    private var listener: (()->Unit)? =null

    init {
        ObjectAnimator.ofArgb(ivScene2, "backgroundColor", 0x00_00_00_00, 0x66_00_00_00).apply {
            cancel()
            startDelay = 200
            duration = 1500
            start()
        }.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                ivScene3.animate().setDuration(1500).alpha(1f)
//                        .setInterpolator(DecelerateInterpolator())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            ivScene3.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            ivScene3.setOnTouchListener { v, event ->
                                if (event.action == MotionEvent.ACTION_DOWN) {
                                    val bitmap = ivScene3.drawToBitmap()
                                    val pixel = bitmap.getPixel(event.x.toInt(), event.y.toInt())
                                    val argb = Integer.toHexString(pixel)
                                    viewColor.setBackgroundColor(pixel)
                                    listener?.invoke()
                                }
                                return@setOnTouchListener false
                            }
                        }
                    })
            }
        })
    }

    fun setOnShinyListener(l:()->Unit){
        listener = l
    }
}