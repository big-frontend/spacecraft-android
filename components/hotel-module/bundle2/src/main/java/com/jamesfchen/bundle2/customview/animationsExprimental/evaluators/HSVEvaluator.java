package com.jamesfchen.bundle2.customview.animationsExprimental.evaluators;

import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.util.Log;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/25/2018  Sun
 */
public class HSVEvaluator implements TypeEvaluator<Integer> {
    //result = startValue + t * (endValue - startValue)
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] currentHsv = new float[3];

        Color.colorToHSV(startValue, startHsv);
        Color.colorToHSV(endValue, endHsv);
        //result = startValue + t * (endValue - startValue) = (1-t)*startValue + t * endValue  线性关系
        for (int i = 0; i < 3; i++)
            currentHsv[i] = startHsv[i] + fraction * (endHsv[i] - startHsv[i]);

        while (currentHsv[0] >= 360.0f) currentHsv[0] -= 360.0f;
        while (currentHsv[0] < 0.0f) currentHsv[0] += 360.0f;
        Log.d(HSVEvaluator.class.getSimpleName(), "section:" + startValue + "/" + endValue + "--->fraction:" + fraction+
                "--->current value:"+currentHsv);
        return Color.HSVToColor(currentHsv);
    }
}
