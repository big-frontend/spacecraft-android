package com.hawksjamesf.simpleweather.bean.fifteendaysbean;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public  class TempeBean {
    private String date;
    private float max;
    private float avg;
    private float min;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "TempeBean{" +
                "date='" + date + '\'' +
                ", max=" + max +
                ", avg=" + avg +
                ", min=" + min +
                '}';
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }


}



