package com.hawksjamesf.simpleweather.util;

import com.hawksjamesf.simpleweather.R;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class ConditionUtil {

    public static int getDayWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.mipmap.w0;
            case "PARTLY_CLOUDY_DAY":
                return R.mipmap.w1;
            case "CLOUDY":
                return R.mipmap.w2;
            case "雷阵雨":
                return R.mipmap.w4;
            case "雨夹雪":
                return R.mipmap.w6;
            case "RAIN":
                return R.mipmap.w7;
            case "中雨":
                return R.mipmap.w8;
            case "大雨":
                return R.mipmap.w9;
            case "暴雨":
                return R.mipmap.w10;
            case "大雪":
                return R.mipmap.w17;
            case "中雪":
                return R.mipmap.w16;
            case "冰雹":
                return R.mipmap.w15;
        }
        return R.mipmap.w0;
    }

    public static int getNightWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.mipmap.w30;
            case "PARTLY_CLOUDY_NIGHT":
                return R.mipmap.w31;
            case "CLOUDY":
                return R.mipmap.w2;
            case "雷阵雨":
                return R.mipmap.w4;
            case "雨夹雪":
                return R.mipmap.w6;
            case "RAIN":
                return R.mipmap.w7;
            case "中雨":
                return R.mipmap.w8;
            case "大雨":
                return R.mipmap.w9;
            case "暴雨":
                return R.mipmap.w10;
            case "大雪":
                return R.mipmap.w17;
            case "中雪":
                return R.mipmap.w16;
            case "冰雹":
                return R.mipmap.w15;
        }
        return R.mipmap.w30;
    }
}
