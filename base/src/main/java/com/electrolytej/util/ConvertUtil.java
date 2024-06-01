package com.electrolytej.util;

import androidx.annotation.FloatRange;

import com.electrolytej.util.Util;


public class ConvertUtil {

    public static int dp2px(@FloatRange final float dpValue) {
        final float scale = Util.getApp().getBaseContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dp(final float pxValue) {
        final float scale = Util.getApp().getBaseContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
