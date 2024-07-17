package com.electrolytej.main.widget

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.electrolytej.main.R


class BreatheAdView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : FrameLayout(ctx, attrs, defStyleAttr) {
    private val root = View.inflate(ctx, R.layout.view_breathe_ad, this)
    private val ivBreathe by lazy { root.findViewById<ImageView>(R.id.iv_breathe) }
    private val ivBreathe2 by lazy { root.findViewById<ImageView>(R.id.iv_breathe2) }
    private val tvIntro by lazy { root.findViewById<TextView>(R.id.tv_intro) }
    private val ivEnter by lazy { root.findViewById<ImageView>(R.id.iv_enter) }

    init {
        val a1 = (AnimatorInflater.loadAnimator(context, R.animator.breathe) as AnimatorSet).apply {
            cancel()
            setTarget(ivBreathe)
            start()
        }
        val a2 =
            (AnimatorInflater.loadAnimator(context, R.animator.breathe2) as AnimatorSet).apply {
                cancel()
                startDelay = 200
                setTarget(ivBreathe2)
                start()
            }
        (AnimatorInflater.loadAnimator(context, R.animator.breathe3) as AnimatorSet).apply {
            cancel()
            startDelay = 200
            setTarget(tvIntro)
            start()
        }
        (AnimatorInflater.loadAnimator(context, R.animator.breathe4) as AnimatorSet).apply {
            cancel()
            startDelay = 200
            setTarget(ivEnter)
            start()
        }
    }
}