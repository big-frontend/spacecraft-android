package com.electrolytej.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.Size;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
public class NumberUtil {
    /**
     * Integer对象之间不能用==，不然会出现不相等情况。因为IntegerCached对象只存储-128到127的值，
     * 超出范围的时候回重新new Integer，那么指向的对象就不同了。
     *
     * @param s
     * @return
     */
    public Integer toInt(String s) {
        return toInt(s, 10);
    }

    public static int toInt(String n, int radix) {
        try {
            return Integer.parseInt(n, radix);
        } catch (Exception e) {
            return -1;
        }
    }

    public Long toLong(String s) {
        return toLong(s, 10);
    }

    public static long toLong(String n, int radix) {
        try {
            return Long.parseLong(n, radix);
        } catch (Exception e) {
            return -1L;
        }
    }

    public static float toFloat(String n) {
        try {
            return Float.parseFloat(n);
        } catch (Exception e) {
            return -1f;
        }
    }

    public Double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return -1d;
        }
    }

    @ColorInt
    public int toColor(@Size(min = 1) String colorString) {
        try {
            return Color.parseColor(colorString);
        } catch (Exception e) {
            return -1;
        }
    }


    /**
     * 保留指定位数的小数（四舍五入）
     *
     * @param value 原始浮点数
     * @param scale 保留的小数位数
     * @return 保留指定位数后的浮点数
     */
    public static float round(float value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须大于等于0");
        }
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    /**
     * 保留指定位数的小数（四舍五入）
     *
     * @param value 原始双精度浮点数
     * @param scale 保留的小数位数
     * @return 保留指定位数后的双精度浮点数
     */
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须大于等于0");
        }
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 保留指定位数的小数（可指定舍入模式）
     *
     * @param value        原始浮点数
     * @param scale        保留的小数位数
     * @param roundingMode 舍入模式
     * @return 保留指定位数后的浮点数
     */
    public static float round(float value, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须大于等于0");
        }
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(scale, roundingMode);
        return bd.floatValue();
    }

    /**
     * 保留指定位数的小数（可指定舍入模式）
     *
     * @param value        原始双精度浮点数
     * @param scale        保留的小数位数
     * @param roundingMode 舍入模式
     * @return 保留指定位数后的双精度浮点数
     */
    public static double round(double value, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须大于等于0");
        }
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }
}
