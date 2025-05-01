package com.electrolytej.ad.widget.sensor;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SensorLineChartView extends View {
    // 数据列表 (时间戳, 值1, 值2, 值3)
    private List<DataPoint> dataPoints = new ArrayList<>();

    // 画笔
    private Paint axisPaint;
    private Paint linePaint1, linePaint2, linePaint3;
    private Paint textPaint;

    // 颜色
    private int lineColor1 = Color.parseColor("#FF5722"); // 橙色
    private int lineColor2 = Color.parseColor("#4CAF50"); // 绿色
    private int lineColor3 = Color.parseColor("#2196F3"); // 蓝色

    // 边距和间距
    private int paddingLeft = 100;
    private int paddingRight = 50;
    private int paddingTop = 50;
    private int paddingBottom = 100;

    // 坐标轴相关
    private int xAxisCount = 10; // X轴刻度数量
    private int yAxisCount = 8;  // Y轴刻度数量

    // 数据范围
    private double minValue = 0;
    private double maxValue = 100;
    private long minTime = 0;
    private long maxTime = 10000; // 10秒
    private String  label1 = "x轴";
    private String  label2 = "y轴";
    private String  label3 = "z轴";

    public SensorLineChartView(Context context) {
        super(context);
        init();
    }

    public SensorLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 坐标轴画笔
        axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(2f);
        axisPaint.setStyle(Paint.Style.STROKE);

        // 文本画笔
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // 折线1画笔
        linePaint1 = new Paint();
        linePaint1.setColor(lineColor1);
        linePaint1.setStrokeWidth(4f);
        linePaint1.setStyle(Paint.Style.STROKE);
        linePaint1.setAntiAlias(true);

        // 折线2画笔
        linePaint2 = new Paint();
        linePaint2.setColor(lineColor2);
        linePaint2.setStrokeWidth(4f);
        linePaint2.setStyle(Paint.Style.STROKE);
        linePaint2.setAntiAlias(true);

        // 折线3画笔
        linePaint3 = new Paint();
        linePaint3.setColor(lineColor3);
        linePaint3.setStrokeWidth(4f);
        linePaint3.setStyle(Paint.Style.STROKE);
        linePaint3.setAntiAlias(true);
    }

    // 数据点类
    public static class DataPoint {
        public long timestamp;
        public double value1;
        public double value2;
        public double value3;

        public DataPoint(long timestamp, double value1, double value2, double value3) {
            this.timestamp = timestamp;
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
        }
    }

    // 添加数据点
    public void addDataPoint(DataPoint point) {
        dataPoints.add(point);

        // 更新数据范围
        updateDataRange();

        // 重绘
        invalidate();
    }

    // 清除所有数据
    public void clearData() {
        dataPoints.clear();
        invalidate();
//        postInvalidate();
    }


    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    // 更新数据范围
    private void updateDataRange() {
        if (dataPoints.isEmpty()) return;

        minTime = dataPoints.get(0).timestamp;
        maxTime = dataPoints.get(0).timestamp;
        minValue = dataPoints.get(0).value1;
        maxValue = dataPoints.get(0).value1;

        for (DataPoint point : dataPoints) {
            minTime = Math.min(minTime, point.timestamp);
            maxTime = Math.max(maxTime, point.timestamp);

            minValue = Math.min(minValue, point.value1);
            minValue = Math.min(minValue, point.value2);
            minValue = Math.min(minValue, point.value3);

            maxValue = Math.max(maxValue, point.value1);
            maxValue = Math.max(maxValue, point.value2);
            maxValue = Math.max(maxValue, point.value3);
        }

        // 添加一些边距
        double range = maxValue - minValue;
        minValue = minValue - range * 0.1f;
        maxValue = maxValue + range * 0.1f;

        // 确保最小值不为负
//        if (minValue < 0 && maxValue > 0) {
//            minValue = 0;
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // 绘制背景
        canvas.drawColor(Color.WHITE);

        // 绘制坐标轴
        drawAxis(canvas, width, height);

        // 绘制折线
        if (!dataPoints.isEmpty()) {
            drawLineChart(canvas, width, height, linePaint1, 1);
            drawLineChart(canvas, width, height, linePaint2, 2);
            drawLineChart(canvas, width, height, linePaint3, 3);
        }

        // 绘制图例
        drawLegend(canvas, width, height);
    }

    // 绘制坐标轴
    private void drawAxis(Canvas canvas, int width, int height) {
        // X轴
        canvas.drawLine(paddingLeft, height - paddingBottom, width - paddingRight, height - paddingBottom, axisPaint);

        // Y轴
        canvas.drawLine(paddingLeft, paddingTop, paddingLeft, height - paddingBottom, axisPaint);

        // 绘制X轴刻度
        float xAxisLength = width - paddingLeft - paddingRight;
        float xStep = xAxisLength / xAxisCount;

        for (int i = 0; i <= xAxisCount; i++) {
            float x = paddingLeft + i * xStep;
            canvas.drawLine(x, height - paddingBottom, x, height - paddingBottom + 10, axisPaint);

            // 时间标签 (秒)
            float time = minTime + (maxTime - minTime) * i / xAxisCount;
            String label = String.format("%.1fs", time / 1000f);
            canvas.drawText(label, x, height - paddingBottom + 40, textPaint);
        }

        // 绘制Y轴刻度
        float yAxisLength = height - paddingTop - paddingBottom;
        float yStep = yAxisLength / yAxisCount;

        for (int i = 0; i <= yAxisCount; i++) {
            float y = height - paddingBottom - i * yStep;
            canvas.drawLine(paddingLeft, y, paddingLeft - 10, y, axisPaint);

            // 值标签
            double value = minValue + (maxValue - minValue) * i / yAxisCount;
            String label = String.format("%.1f", value);
            canvas.drawText(label, paddingLeft - 30, y + 10, textPaint);
        }

        // 绘制轴标签
        canvas.drawText("时间 (s)", width / 2, height - 20, textPaint);
        canvas.save();
        canvas.rotate(-90, paddingLeft - 60, height / 2);
        canvas.drawText("传感器值", paddingLeft - 60, height / 2, textPaint);
        canvas.restore();
    }

    // 绘制折线图
    private void drawLineChart(Canvas canvas, int width, int height, Paint paint, int valueType) {
        if (dataPoints.size() < 2) return;

        float xAxisLength = width - paddingLeft - paddingRight;
        float yAxisLength = height - paddingTop - paddingBottom;

        Path path = new Path();
        boolean firstPoint = true;

        for (DataPoint point : dataPoints) {
            // 计算X坐标 (时间)
            float x = paddingLeft + ((point.timestamp - minTime) / (float)(maxTime - minTime)) * xAxisLength;

            // 计算Y坐标 (值)
            double value = 0;
            switch (valueType) {
                case 1: value = point.value1; break;
                case 2: value = point.value2; break;
                case 3: value = point.value3; break;
            }

            double y = height - paddingBottom - ((value - minValue) / (maxValue - minValue)) * yAxisLength;

            if (firstPoint) {
                path.moveTo(x, (float) y);
                firstPoint = false;
            } else {
                path.lineTo(x, (float) y);
            }
        }

        canvas.drawPath(path, paint);
    }



    // 绘制图例
    private void drawLegend(Canvas canvas, int width, int height) {
//        float legendX = width - paddingRight + 20;
        float legendX = paddingLeft + 20;
        float legendY = paddingTop + 50;
        float rectSize = 30;
        float textOffset = rectSize + 30;

        // 图例1
        canvas.drawRect(legendX, legendY, legendX + rectSize, legendY + rectSize, linePaint1);
        canvas.drawText(label1, legendX + textOffset, legendY + rectSize / 2 + 10, textPaint);

        // 图例2
        legendY += 50;
        canvas.drawRect(legendX, legendY, legendX + rectSize, legendY + rectSize, linePaint2);
        canvas.drawText(label2, legendX + textOffset, legendY + rectSize / 2 + 10, textPaint);

        // 图例3
        legendY += 50;
        canvas.drawRect(legendX, legendY, legendX + rectSize, legendY + rectSize, linePaint3);
        canvas.drawText(label3, legendX + textOffset, legendY + rectSize / 2 + 10, textPaint);
    }
}