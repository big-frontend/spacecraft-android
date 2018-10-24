package com.hawksjamesf.simpleweather.data.bean.fifteendaysbean;

import com.google.gson.Gson;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public  class TempeBean {
    @Override
    public String toString() {
        return "TempeBean{" +
                "date='" + date + '\'' +
                ", max=" + max +
                ", avg=" + avg +
                ", min=" + min +
                '}';
    }

    private String date;
    private double max;
    private double avg;
    private double min;

    public static TempeBean objectFromData(String str) {

        return new Gson().fromJson(str, TempeBean.class);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}



