package com.electrolytej.main.util.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.blankj.utilcode.util.ConvertUtils;

public class RoundedBackgroundSpan  extends ReplacementSpan {

    private static int CORNER_RADIUS = 8;
    private int backgroundColor = 0;
    private int textColor = 0;
    private int radius = 0;
    private int mTop = 0;
    private int mLeft = 0;
    private int mRight = 0;
    private int mBottom = 0;
    public RoundedBackgroundSpan(int backgroundColor,int textColor) {
        this(backgroundColor,textColor,CORNER_RADIUS);
    }

    public RoundedBackgroundSpan(int backgroundColor,int textColor,int radius) {
        super();
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.radius = radius;
        mTop = ConvertUtils.dp2px(4f);
        mLeft =ConvertUtils.dp2px(2.5f);
        mBottom = ConvertUtils.dp2px(2.5f);
        mRight = ConvertUtils.dp2px(2.5f);
    }

    public void setPadding(int top,int left,int bottom,int right){
        this.mTop = top;
        this.mLeft = left;
        this.mBottom = bottom;
        this.mRight = right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
//        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        // fm.bottom - fm.top 解决设置行距（android:lineSpacingMultiplier="1.2"）时背景色高度问题
        float textHeight = fm.bottom - fm.top;
        float textHeight2 = fm.descent -fm.ascent;

        RectF rect = new RectF(x,  top+mTop, x + measureText(paint, text, start, end) + mRight + mLeft, bottom);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x+mLeft, y, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm){
        return Math.round(paint.measureText(text, start, end));
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
