package com.hawksjamesf.spacecraft.interpolators;

import android.animation.TimeInterpolator;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class CustomInterpolator implements TimeInterpolator {
    /**
     *
     * @param v [0,1]，0表示0%，1表示100%。 动画的已执行时间
     * @return
     */
    @Override
    public float getInterpolation(float v) {
        //y=8x
        return 8 * v;
    }
}
