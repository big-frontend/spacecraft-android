package com.hawksjamesf.simpleweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hawksjamesf.simpleweather.bean.TempeBean;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/6/29
 */

public class FifteenDaysView extends LinearLayout {
    private Paint dayPaint;
    private Paint nightPaint;

    protected Path pathDay;
    protected Path pathNight;

    private int dayLineColor = 0xff78ad23;
    private int nightLineColor = 0xff23acb3;
    private float lineWidth = 4f;
    private List<TempeBean> mTempeBeans;
    private Context ct;

    public FifteenDaysView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ct=context;
        dayPaint = new Paint();
        dayPaint.setColor(dayLineColor);
        dayPaint.setAntiAlias(true);
        dayPaint.setStrokeWidth(lineWidth);
        dayPaint.setStyle(Paint.Style.STROKE);

        nightPaint = new Paint();
        nightPaint.setColor(nightLineColor);
        nightPaint.setAntiAlias(true);
        nightPaint.setStrokeWidth(lineWidth);
        nightPaint.setStyle(Paint.Style.STROKE);

        pathDay = new Path();
        pathNight = new Path();
    }
//    private int getScreenWidth() {
//        WindowManager wm = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay().getWidth();
//    }
//    private int getMaxDayTemp(List<WeatherModel> list) {
//        if (list != null) {
//            return Collections.max(list, new DayTempComparator()).getDayTemp();
//        }
//        return 0;
//    }
//    private int getMaxNightTemp(List<WeatherModel> list) {
//        if (list != null) {
//            return Collections.max(list, new NightTempComparator()).getNightTemp();
//        }
//        return 0;
//    }
//    private int getMinDayTemp(List<WeatherModel> list) {
//        if (list != null) {
//            return Collections.min(list, new DayTempComparator()).getDayTemp();
//        }
//        return 0;
//    }
//    private int getMinNightTemp(List<WeatherModel> list) {
//        if (list != null) {
//            return Collections.min(list, new NightTempComparator()).getNightTemp();
//        }
//        return 0;
//    }
    public void setData(List<TempeBean> tempeBeans) {
        mTempeBeans = tempeBeans;
        for (int i=0;i<5;i++) {
            View itemView= LayoutInflater.from(ct).inflate(R.layout.item_fifteen_days_chart,null);
            addView(itemView);
            Logger.d(itemView);

        }
        invalidate();


    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getChildCount() > 0) {
            ViewGroup root = (ViewGroup) getChildAt(0);
            Logger.d(root);
            for (int i = 0; i < root.getChildCount(); i++) {
                Logger.d(getChildAt(i));
            }

            if (root.getChildCount() > 0) {

//                TemperatureView tempeView = (TemperatureView)root.getChildAt(1);
                TemperatureView tempeView = (TemperatureView) root.findViewById(R.id.ttv_day);
//
                float curDayX = tempeView.getX();
                float curDayY = tempeView.getY();
                float curNightX = tempeView.getX();
                float curNightY = tempeView.getY();
//                Logger.d("nightX:"+nightX+"---nightY:"+nightY,tempeView);

                tempeView.setRadius(10);
//
                int deltaDayX = (int) (curDayX + tempeView.getxPointDay());
                int deltaDayY = (int) (curDayY + tempeView.getyPointDay());
                int deltaNightX = (int) (curNightX + tempeView.getxPointNight());
                int deltaNightY = (int) (curNightY + tempeView.getyPointNight());
//
                pathDay.reset();
                pathNight.reset();

                pathDay.moveTo(deltaDayX, deltaDayY);
                pathNight.moveTo(deltaNightX, deltaNightY);
                    //折线
                    for (int i = 0; i < root.getChildCount() - 1; i++) {
                        View curItemView =getChildAt(i);
                        View nextItemView=getChildAt(i+1);

                        TemperatureView curTempe = (TemperatureView) getChildAt(i).findViewById(R.id.ttv_day);
                        TemperatureView nextTempe = (TemperatureView) getChildAt(i+1).findViewById(R.id.ttv_day);
                        //current temperature
                        int curDayX1 = (int) (curTempe.getX() + curItemView.getWidth() * i);
                        int curDayY1= (int) curTempe.getY();
                        int curNightX1 = (int) (curTempe.getX() + curItemView.getWidth() * i);
                        int curNightY1 = (int) curTempe.getY();
                        //next temperature

                        int nextDayX1 = (int) (nextTempe.getX() + nextItemView.getWidth() * i+1);
                        int nextDayY1 = (int) nextTempe.getY();
                        int nextNightX1 = (int) (nextTempe.getX() + nextItemView.getWidth() * i+1);
                        int nextNightY1 = (int) nextTempe.getY();


                        curTempe.setRadius(10);
                        nextTempe.setRadius(10);

                        int x1 = curDayX1 + curTempe.getxPointDay();
                        int y1 = curDayY1 + curTempe.getyPointDay();
                        int x2 = curNightX1 + curTempe.getxPointNight();
                        int y2 = curNightY1 + curTempe.getyPointNight();

                        int x11 = nextDayX1 + nextTempe.getxPointDay();
                        int y11 = nextDayY1 + nextTempe.getyPointDay();
                        int x22 = nextNightX1 + nextTempe.getxPointNight();
                        int y22 = nextNightY1 + nextTempe.getyPointNight();

                        canvas.drawLine(x1, y1, x11, y11, dayPaint);
                        canvas.drawLine(x2, y2, x22, y22, nightPaint);


                    }
            }

        }
    }


}
