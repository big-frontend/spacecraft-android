package com.electrolytej.bundle2.page.customview.animationsExprimental.interpolators;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/25/2018  Sun
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.BaseInterpolator;

/**
 * An interpolator where the rate of change is constant
 */
@SuppressLint("NewApi")
public class LinearInterpolator extends BaseInterpolator {
    public LinearInterpolator() {
    }
    public LinearInterpolator(Context context, AttributeSet attrs) {
    }
    public float getInterpolation(float input) {
        return input;
    }
//    /** @hide */
//    @Override
//    public long createNativeInterpolator() {
//        return NativeInterpolatorFactoryHelper.createLinearInterpolator();
//    }
}