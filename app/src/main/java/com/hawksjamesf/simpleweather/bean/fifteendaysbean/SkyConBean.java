package com.hawksjamesf.simpleweather.bean.fifteendaysbean;
/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public  class SkyConBean {
    private String value;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SkyConBean{" +
                "value='" + value + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public void setValue(String value) {
        this.value = value;
    }
}