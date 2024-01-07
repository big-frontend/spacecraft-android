package com.jamesfchen.bundle2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SignatureView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : View(ctx, attrs, defStyleAttr) {
    private val paint = Paint()
    private val path = Path()
    private var lastX = 0f
    private var lastY = 0f
    lateinit var bitmap: Bitmap
//    private lateinit var canvas: Canvas

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE
    }

    //完成measure 并且，在layout 确定frame(setFrame)才会调用该方法
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
//        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        canvas = Canvas(bitmap)
//        canvas.drawColor(Color.WHITE)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
//                path.reset()
                lastX = event.x
                lastY = event.y
                path.moveTo(lastX, lastY)
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = event.x
                val curY = event.y
                path.quadTo(lastX, lastY, (curX + lastX) / 2, (curY + lastY) / 2)
                lastX = curX
                lastY = curY
            }
            MotionEvent.ACTION_UP -> {
//                canvas.drawPath(path, paint)
//                path.reset()
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.let {
//            it.drawBitmap(bitmap, 0f, 0f, paint)
            it.drawPath(path, paint)
        }
    }

    override fun invalidate() {
        super.invalidate()
        isDrawingCacheEnabled = false
    }

    fun getSignatureText(): Bitmap {
        isDrawingCacheEnabled = true
        return drawingCache
    }
}