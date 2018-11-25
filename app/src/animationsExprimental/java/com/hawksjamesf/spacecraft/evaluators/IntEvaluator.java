package com.hawksjamesf.spacecraft.evaluators;

import android.animation.TypeEvaluator;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class IntEvaluator implements TypeEvaluator<Integer> {
    /**
     * This function returns the result of linearly interpolating the start and end values, with
     * <code>fraction</code> representing the proportion between the start and end values. The
     * calculation is a simple parametric calculation: <code>result = x0 + t * (x1 - x0)</code>,
     * where <code>x0</code> is <code>startValue</code>, <code>x1</code> is <code>endValue</code>,
     * and <code>t</code> is <code>fraction</code>.
     *
     * @param fraction   The fraction from the starting to the ending values
     * @param startValue The start value; should be of type <code>int</code> or
     *                   <code>Integer</code>
     * @param endValue   The end value; should be of type <code>int</code> or <code>Integer</code>
     * @return A linear interpolation between the start and end values, given the
     *         <code>fraction</code> parameter.
     */
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }
}