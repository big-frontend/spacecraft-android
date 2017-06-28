package com.hawksjamesf.simpleweather.bean;


public  class TempeBean {
    private String date;
    private int max;
    private double avg;
    private int min;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMax() {
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

    public void setMax(int max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }


}



