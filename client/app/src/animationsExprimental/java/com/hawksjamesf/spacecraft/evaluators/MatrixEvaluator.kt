package com.hawksjamesf.spacecraft.evaluators

import android.animation.TypeEvaluator
import android.graphics.Matrix


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
class MatrixEvaluator : TypeEvaluator<Matrix> {
    override fun evaluate(fraction: Float, startValue: Matrix?, endValue: Matrix?): Matrix {
        val startEntries = FloatArray(9)
        val endEntries = FloatArray(9)
        val currentEntries = FloatArray(9)

        startValue?.getValues(startEntries)
        endValue?.getValues(endEntries)

        for (i in 0..8)
            currentEntries[i] = (1 - fraction) * startEntries[i] + fraction * endEntries[i]

        val matrix = Matrix()
        matrix.setValues(currentEntries)
        return matrix
    }

}