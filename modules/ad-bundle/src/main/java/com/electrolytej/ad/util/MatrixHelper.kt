package com.electrolytej.ad.util

import android.opengl.Matrix

object MatrixHelper {
    fun perspectiveM(matrix: FloatArray, fovy: Float, aspect: Float, zNear: Float, zFar: Float) {
        Matrix.perspectiveM(matrix, 0, fovy, aspect, zNear, zFar)
    }

    fun setLookAtM(matrix: FloatArray,
                   eyeX: Float, eyeY: Float, eyeZ: Float,
                   centerX: Float, centerY: Float, centerZ: Float,
                   upX: Float, upY: Float, upZ: Float) {
        Matrix.setLookAtM(matrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
    }

    fun multiplyMM(result: FloatArray, left: FloatArray, right: FloatArray) {
        Matrix.multiplyMM(result, 0, left, 0, right, 0)
    }
}