package com.hawksjamesf.spacecraft.data.bean.fifteendaysbean;

import com.google.gson.Gson;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public  class SkyConBean {
    @Override
    public String toString() {
        return "SkyConBean{" +
                "date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    private String date;
    private String value;

    public static SkyConBean objectFromData(String str) {

        return new Gson().fromJson(str, SkyConBean.class);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}