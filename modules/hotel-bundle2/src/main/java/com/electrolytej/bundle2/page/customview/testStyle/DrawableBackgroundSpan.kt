package com.electrolytej.bundle2.page.customview.testStyle

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.text.TextPaint
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import com.blankj.utilcode.util.ConvertUtils
import com.electrolytej.util.DP

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/14/2019  Sat
 */


class DrawableBackgroundSpan(val bgColor: Int = -1, @DP val strokeWith: Int = -1, @ColorInt val strokeColor: Int = -1, @DP val radius: Int = -1, @DP val dashWidth: Int = -1, @DP val dashGap: Int = -1, val textColor: Int = -1) : ReplacementSpan() {

    var size: Float = 0f
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {

        size = paint.measureText(text, start, end)
        return size.toInt()
    }

    override fun updateDrawState(ds: TextPaint?) {
        super.updateDrawState(ds)
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val originalColor = paint.color
        val originalTextSize = paint.textSize
        val originalStyle = paint.style
        val originalStrokeWith = paint.strokeWidth

        if (textColor != -1) {
            paint.color = textColor
            paint.style = Paint.Style.FILL
            canvas.drawText(text.toString(), start, end, x, y.toFloat(), paint)
        }
        paint.isAntiAlias = true
        if (strokeWith != -1 && strokeColor != -1) {
            paint.strokeWidth = ConvertUtils.dp2px(strokeWith.toFloat()).toFloat()
            paint.style = Paint.Style.STROKE
            paint.color = strokeColor
            if (dashGap != -1 && dashWidth != -1) paint.pathEffect = DashPathEffect(floatArrayOf(
                ConvertUtils.dp2px(dashWidth.toFloat()).toFloat(), ConvertUtils.dp2px(dashGap.toFloat()).toFloat()), 0f)
        }
        if (bgColor != -1) {
            paint.color = bgColor
            paint.style = Paint.Style.FILL
        }
//        if (bgColor == strokeColor) {
//            paint.style = Paint.Style.FILL_AND_STROKE
//        }
        val rectLeft = x
        val rectTop = top.toFloat()
        val rectRight = y.toFloat()
        val rectBottom = bottom.toFloat()
        if (radius != -1) {
            canvas.drawRoundRect(rectLeft, rectTop, x + size, rectBottom, ConvertUtils.dp2px(radius.toFloat()).toFloat(), ConvertUtils.dp2px(radius.toFloat()).toFloat(), paint)
        } else {
            canvas.drawRect(rectLeft, rectTop, x + size, rectBottom, paint)
        }

        paint.color = originalColor
        paint.textSize = originalTextSize
        paint.style = originalStyle
        paint.strokeWidth = originalStrokeWith

    }
}