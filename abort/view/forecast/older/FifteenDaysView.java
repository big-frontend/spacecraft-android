package com.hawksjamesf.spacecraft.ui.home.view.forecast.older;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hawksjamesf.spacecraft.R;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class FifteenDaysView extends LinearLayout {
    private Paint dayPaint;
    private Paint nightPaint;

    protected Path pathDay;
    protected Path pathNight;

    private int dayLineColor = 0xff78ad23;
    private int nightLineColor = 0xffe8d318;
    private float lineWidth = 4f;
//    private List<TempeBean> mTempeBeans;
//    private List<SkyConBean> mSkyConBeans;

    public FifteenDaysView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        setWillNotDraw(false);

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int measurewidth=0;
//        int measureHeight=0;
//        int childCount = getChildCount();
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
//
//
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (childCount==0){
//
//        }else if (widthSpecMode==MeasureSpec.AT_MOST && heightSpecMode==MeasureSpec.AT_MOST){
//            View childAt = getChildAt(0);
//            measurewidth=childCount*childAt.getMeasuredWidth();
//            measureHeight=childAt.getMeasuredHeight();
//            setMeasuredDimension(measurewidth,measureHeight);
//        }else if (widthMeasureSpec==MeasureSpec.AT_MOST){
//            View childAt = getChildAt(0);
//            measurewidth=childCount*childAt.getMeasuredWidth();
//            setMeasuredDimension(measurewidth,heightSpecMode);
//
//        }else  if (heightMeasureSpec==MeasureSpec.AT_MOST){
//            View childAt = getChildAt(0);
//            measureHeight=childAt.getMeasuredHeight();
//            setMeasuredDimension(widthSpecSize,measureHeight);
//        }
//
//    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        if (mTempeBeans == null || mSkyConBeans == null) return;
        if (getChildCount() > 0) {
//            Logger.d(getChildCount());
            ViewGroup root = (ViewGroup) getChildAt(0);


            if (root.getChildCount() > 0) {
                //折线
                for (int i = 0; i < getChildCount() - 1; i++) {
                    View curItemView = getChildAt(i);
                    View nextItemView = getChildAt(i + 1);
//                    Logger.d(getChildAt(i));

                    TemperatureView curTempe = (TemperatureView) curItemView.findViewById(R.id.ttv_day);
//                    /*if (i==0)*/ Logger.d(curTempe);
                    TemperatureView nextTempe = (TemperatureView) nextItemView.findViewById(R.id.ttv_day);

                    /**
                     * day line chart
                     */
                    //current temperature point
                    int curDayX1 = (int) (curTempe.getX() + curItemView.getWidth() * i);
                    int curDayY1 = (int) curTempe.getY();
                    int x1 = curDayX1 + curTempe.getxPointDay();
                    int y1 = curDayY1 + curTempe.getyPointDay();
//                    if (i==0) Logger.d(curTempe.getxPointDay()+","+ curTempe.getyPointDay());
                    //next temperature point
                    int nextDayX1 = (int) (nextTempe.getX() + nextItemView.getWidth() * (i + 1));
                    int nextDayY1 = (int) nextTempe.getY();
                    int x11 = nextDayX1 + nextTempe.getxPointDay();
                    int y11 = nextDayY1 + nextTempe.getyPointDay();
//                    Logger.d("curTempe:" + x1 + "," + y1 + "--" + x11 + "," + y11);
                    canvas.drawLine(x1, y1, x11, y11, dayPaint);

                    /**
                     * night line chart
                     */
                    int curNightX1 = (int) (curTempe.getX() + curItemView.getWidth() * i);
                    int curNightY1 = (int) curTempe.getY();
                    int x2 = curNightX1 + curTempe.getxPointNight();
                    int y2 = curNightY1 + curTempe.getyPointNight();
//                    Logger.d(curTempe.getxPointNight()+","+ curTempe.getyPointNight());

                    int nextNightX1 = (int) (nextTempe.getX() + nextItemView.getWidth() * (i + 1));
                    int nextNightY1 = (int) nextTempe.getY();
                    int x22 = nextNightX1 + nextTempe.getxPointNight();
                    int y22 = nextNightY1 + nextTempe.getyPointNight();
//                    Logger.d("nextTempe:"+ x2 + "," + y2 + "--" + x22 + "," + y22);
                    canvas.drawLine(x2, y2, x22, y22, nightPaint);


                }
            }

        }


    }

//    private static class DayTempComparator implements Comparator<TempeBean> {
//
//        @Override
//        public int compare(TempeBean o1, TempeBean o2) {
//            if (o1.getMax() == o2.getMax()) {
//                return 0;
//            } else if (o1.getMax() > o2.getMax()) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
//    }
//
//    private static class NightTempComparator implements Comparator<TempeBean> {
//
//        @Override
//        public int compare(TempeBean o1, TempeBean o2) {
//            if (o1.getMin() == o2.getMin()) {
//                return 0;
//            } else if (o1.getMin() > o2.getMin()) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
//    }
//
//    private int getFiveDaysMaxTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.max(tempeBeans, new DayTempComparator()).getMax();
//        }
//        return 0;
//    }
//
//    private int getFiveDayMinTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.min(tempeBeans, new DayTempComparator()).getMax();
//        }
//        return 0;
//    }
//
//    private int getFiveNightsMaxTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.max(tempeBeans, new NightTempComparator()).getMin();
//        }
//        return 0;
//    }

/*    private int getFiveNightsMinTempe(List<TempeBean> tempeBeans) {
        if (tempeBeans != null) {
            return (int) Collections.min(tempeBeans, new NightTempComparator()).getMin();
        }
        return 0;
    }

    public void setData(List<TempeBean> tempeBeans, List<SkyConBean> skyconBeans) {
        mTempeBeans = tempeBeans;
        mSkyConBeans = skyconBeans;
        if (tempeBeans == null || skyconBeans == null) return;

        int fiveDaysMaxTempe = getFiveDaysMaxTempe(tempeBeans);
        int fiveDayMinTempe = getFiveDayMinTempe(tempeBeans);
        int fiveNightsMaxTempe = getFiveNightsMaxTempe(tempeBeans);
        int fiveNightsMinTempe = getFiveNightsMinTempe(tempeBeans);

        int max = fiveDaysMaxTempe > fiveNightsMaxTempe ? fiveDaysMaxTempe : fiveNightsMaxTempe;
        int min = fiveDayMinTempe < fiveNightsMinTempe ? fiveDayMinTempe : fiveNightsMinTempe;
        for (int i = 0; i < tempeBeans.size(); i++) {
            TempeBean tempeBean = tempeBeans.get(i);
            View itemView = getChildAt(i);
            TemperatureView tempeView = (TemperatureView) itemView.findViewById(R.id.ttv_day);
            tempeView.setMinTemp(min);
            tempeView.setMaxTemp(max);
            tempeView.setTemperatureDay((int) tempeBean.getMax());
            tempeView.setTemperatureNight((int) tempeBean.getMin());


        }
        for (int j = 0; j < skyconBeans.size(); j++) {
            SkyConBean skyConBean = skyconBeans.get(j);
            String condition = skyConBean.getValue();
//            Logger.d(condition);
            String[] split = skyConBean.getDate().split("-");
            String date=split[1]+"/"+split[2];

            View itemView = getChildAt(j);
            TextView tvWeek = (TextView) itemView.findViewById(R.id.tv_week);
            TextView tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            TextView tvDayWeather = (TextView) itemView.findViewById(R.id.tv_day_weather);
            ImageView ivDayWeather = (ImageView) itemView.findViewById(R.id.iv_day_weather);

            ImageView ivNightWeather = (ImageView) itemView.findViewById(R.id.iv_night_weather);
            TextView tvNightWeather = (TextView) itemView.findViewById(R.id.tv_night_weather);

            ivDayWeather.setImageResource(ConditionUtils.getDayWeatherPic(condition));
            tvDayWeather.setText(condition);
            ivNightWeather.setImageResource(ConditionUtils.getNightWeatherPic(condition));
            tvNightWeather.setText(condition);
            tvDate.setText(date);
//            String curDate = new  SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(new  SimpleDateFormat("yyyy-MM-dd").parse(skyConBean.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String[] weeks = getResources().getStringArray(R.array.weeks);
            tvWeek.setText(weeks[calendar.get(Calendar.DAY_OF_WEEK)-1]);

        }
        invalidate();


    }*/


}
