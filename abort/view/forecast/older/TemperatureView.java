package com.hawksjamesf.spacecraft.ui.home.view.forecast.older;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class TemperatureView extends View {

    private int maxTemp;
    private int minTemp;

    private int temperatureDay;
    private int temperatureNight;

    private Paint pointPaint;
    private Paint textPaint;
    private int pointColor;
    private int textColor;

    private int radius = 6;
    private int textSize = 26;

    private int xPointDay;
    private int yPointDay;
    private int xPointNight;
    private int yPointNight;


    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);

        initPaint(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        textColor = 0xffffffff;
        pointColor = 0xffffffff;
    }

    private void initPaint(Context context, AttributeSet attrs) {

        pointPaint = new Paint();
        textPaint = new Paint();

        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (widthSpecMode==MeasureSpec.AT_MOST && heightSpecMode==MeasureSpec.AT_MOST){
//            setMeasuredDimension(230,300);
//            Logger.d(1);
//        }else if (widthSpecMode==MeasureSpec.AT_MOST){
//            Logger.d(2);
//            setMeasuredDimension(230,heightSpecSize);
//        }else if (heightSpecMode==MeasureSpec.AT_MOST){
//            Logger.d(widthSpecMode+"  "+heightSpecMode);
//            Logger.d(widthSpecSize+"  "+heightSpecSize);
//            setMeasuredDimension(widthSpecSize,300);
//        }
//
//    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int height = getHeight() - textSize * 4;
        int x = getWidth() / 2;
        int y = (int) (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;

        int x2 = getWidth() / 2;
        int y2 = (int) (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        xPointDay = x;
        yPointDay = y;
        xPointNight = x2;
        yPointNight = y2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoint(canvas);
        drawText(canvas);


    }

    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(xPointDay, yPointDay, radius, pointPaint);
        canvas.drawCircle(xPointNight, yPointNight, radius, pointPaint);
    }

    private void drawText(Canvas canvas) {
        int height = getHeight() - textSize * 4;
        int yDay = (int) (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        int yNight = (int) (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        String dayTemp = temperatureDay + "°";
        String nightTemp = temperatureNight + "°";
        float widDay = textPaint.measureText(dayTemp);
        float widNight = textPaint.measureText(nightTemp);
        float hei = textPaint.descent() - textPaint.ascent();
        canvas.drawText(dayTemp, getWidth() / 2 - widDay / 2, yDay - radius - hei / 2, textPaint);
        canvas.drawText(nightTemp, getWidth() / 2 - widNight / 2, yNight + radius + hei, textPaint);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }


    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }


    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getxPointDay() {
        return xPointDay;
    }

    public int getyPointDay() {
        return yPointDay;
    }


    public int getxPointNight() {
        return xPointNight;
    }


    public int getyPointNight() {
        return yPointNight;
    }


    public void setTemperatureDay(int temperatureDay) {
        this.temperatureDay = temperatureDay;
    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


    public void setTemperatureNight(int temperatureNight) {
        this.temperatureNight = temperatureNight;
    }
}
