package com.jamesfchen.myhome.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.Size;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/08/2021  Tue
 * - Color.parseColor
 * - Integer.valueOf
 * - Integer.parseInt
 * - Long.parseLong
 * - Long.valueOf
 * - Float.valueOf
 * - Float.parseFloat
 * - Double.valueOf
 * - Double.parseDouble
 */
public class SafeConvert {
    /**
     * Integer对象之间不能用==，不然会出现不相等情况。因为IntegerCached对象只存储-128到127的值，
     * 超出范围的时候回重新new Integer，那么指向的对象就不同了。
     * @param s
     * @return
     */
    public Integer toInt(String s){
        return Integer.valueOf(s);
    }
    public Integer toInt(String s,int radix){
        return  Integer.valueOf(s,radix);
    }

    public Long toLong(String s){
        return Long.valueOf(s);
    }
    public Long toLong(String s,int radix){
        return  Long.valueOf(s,radix);
    }
    public Float toFloat(String s){
        return Float.valueOf(s);
    }
    public Double toDouble(String s){
        return Double.valueOf(s);
    }
    @ColorInt
    public int toColor(@Size(min=1)  String colorString){
        return Color.parseColor(colorString);
    }
}
