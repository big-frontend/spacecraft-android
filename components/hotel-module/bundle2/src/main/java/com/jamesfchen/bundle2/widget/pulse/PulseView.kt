//package com.jamesfchen.bundle2.pulse
//
//import android.content.Context
//import android.graphics.Path
//import android.graphics.PathMeasure
//import android.util.AttributeSet
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.Animation
//import android.view.animation.LinearInterpolator
//import android.view.animation.Transformation
//import android.widget.FrameLayout
//import java.util.*
//import java.util.concurrent.atomic.AtomicInteger
//
///**
// * 自定义Path https://www.jianshu.com/p/726b714da133
// * https://segmentfault.com/a/1190000021342068#:~:text=%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF%E6%98%AF,%E4%B8%A4%E7%AB%AF%E6%98%AF%E6%8E%A7%E5%88%B6%E7%AB%AF%E7%82%B9%E3%80%82
// */
//class PulseView : FrameLayout {
//    private val mRandom = Random()
//    private val mCounter = AtomicInteger(0)
//
//    constructor(context: Context) : this(context, null)
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
//        context,
//        attrs,
//        defStyleAttr,
//        0
//    )
//
//    constructor(
//        context: Context,
//        attrs: AttributeSet?,
//        defStyleAttr: Int,
//        defStyleRes: Int
//    ) : super(context, attrs, defStyleAttr, defStyleRes) {
//
//    }
//
//    private fun randomRotation() = mRandom.nextFloat() * 28.6f - 14.3f
//    fun emit() {
//        val dancer = HeartView(context)
//        addView(
//            dancer,
//            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        )
//        val dancePath = createPath(mCounter, 2)
//        setLayerType(LAYER_TYPE_HARDWARE, null)
//        val anim = FloatAnimation(dancePath, randomRotation(), this, dancer)
//        anim.duration = 3000
//        anim.interpolator = LinearInterpolator()
//        anim.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationEnd(animation: Animation) {
//                removeView(dancer)
//                mCounter.decrementAndGet()
//            }
//            override fun onAnimationRepeat(animation: Animation) {}
//            override fun onAnimationStart(animation: Animation) {
//                mCounter.incrementAndGet()
//            }
//        })
//        anim.interpolator = LinearInterpolator()
//        dancer.startAnimation(anim)
//    }
//
//    private fun createPath(counter: AtomicInteger, factor: Int): Path {
//        if (pointx <= initX && pointx >= 0) {
//            pointx -= 10
//        } else if (pointx >= -initX && pointx <= 0) {
//            pointx += 10
//        } else pointx = initX
//
//        var x = mRandom.nextInt(mConfig.xRand)
//        var x2 = mRandom.nextInt(mConfig.xRand)
//        val y= view.height - mConfig.initY
//        var y2 = counter.toInt() * 15 + mConfig.animLength * factor + mRandom.nextInt(mConfig.animLengthRand)
//        factor = y2 / mConfig.bezierFactor
//        x = mConfig.xPointFactor + x
//        x2 = mConfig.xPointFactor + x2
//        val y3 = y - y2
//        y2 = y - y2 / 2
//        val p = Path()
//        p.moveTo(mConfig.initX, y.toFloat())
//        p.cubicTo(
//            mConfig.initX,
//            (y - factor).toFloat(),
//            x.toFloat(),
//            (y2 + factor).toFloat(),
//            x.toFloat(),
//            y2.toFloat()
//        )
//        p.moveTo(x.toFloat(), y2.toFloat())
//        p.cubicTo(x.toFloat(), (y2 - factor).toFloat(), x2.toFloat(), (y3 + factor).toFloat(), x2.toFloat(), y3.toFloat())
//        return p
//    }
//
//
//    internal class FloatAnimation(path: Path, private val rotation: Float, private val dancer: View) : Animation() {
//        private val mPm: PathMeasure = PathMeasure(path, false)
//        private val mDistance: Float = mPm.length
//
//        override fun applyTransformation(factor: Float, transformation: Transformation) {
//            val matrix = transformation.matrix
//            mPm.getMatrix(mDistance * factor, matrix, PathMeasure.POSITION_MATRIX_FLAG)
//            dancer.rotation = rotation * factor
//            var scale = 1f
//            if (3000.0f * factor < 200.0f) {
//                scale = scale(
//                    factor.toDouble(),
//                    0.0,
//                    0.06666667014360428,
//                    0.20000000298023224,
//                    1.100000023841858
//                )
//            } else if (3000.0f * factor < 300.0f) {
//                scale = scale(
//                    factor.toDouble(),
//                    0.06666667014360428,
//                    0.10000000149011612,
//                    1.100000023841858,
//                    1.0
//                )
//            }
//            dancer.scaleX = scale
//            dancer.scaleY = scale
//            transformation.alpha = 1.0f - factor
//        }
//
//
//        private fun scale(a: Double, b: Double, c: Double, d: Double, e: Double): Float {
//            return ((a - b) / (c - b) * (e - d) + d).toFloat()
//        }
//    }
//
//
//}