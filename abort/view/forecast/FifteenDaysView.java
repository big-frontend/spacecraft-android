package com.hawksjamesf.simpleweather.ui.home.view.forecast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.ui.home.view.forecast.older.TemperatureView;

/**
 * TODO: document your custom view class.
 */
public class FifteenDaysView extends RecyclerView {
    private int dayLineColor = 0xff78ad23;
    private int nightLineColor = 0xffe8d318;
    private float lineWidth = 4f;



    private Paint dayPaint;
    private Paint nightPaint;

    protected Path pathDay;
    protected Path pathNight;

    public FifteenDaysView(Context context) {
        super(context);
        init(null, 0);
    }

    public FifteenDaysView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FifteenDaysView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

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


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getChildCount() > 0) {
//            Logger.d(getChildCount());
//            ViewGroup root = (ViewGroup) getChildAt(0);


//            if (root.getChildCount() > 0) {
                //折线
                for (int i = 0; i < getChildCount()-1; i++) {
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
