package com.electrolytej.sensor

import kotlin.math.acos
import kotlin.math.sin

/**
 * 将 3×3 旋转矩阵转换为轴–角表示
 * @param R  长度9的矩阵，行优先： [R00,R01,R02, R10,R11,R12, R20,R21,R22]
 * @return   Pair(axis: FloatArray(3), angleRad: Float)
 */
fun rotationMatrixToAxisAngle(R: FloatArray): Pair<FloatArray, Float> {
    // 1. 计算 trace
    val tr = R[0] + R[4] + R[8]
    val cosT = ((tr - 1f) * 0.5f).coerceIn(-1f, 1f)
    val theta = acos(cosT)
    // 2. sinθ
    val sinT = sin(theta)
    val axis = if (sinT < 1e-6f) floatArrayOf(1f, 0f, 0f)
    else floatArrayOf(
        (R[7] - R[5]) / (2f * sinT),
        (R[2] - R[6]) / (2f * sinT),
        (R[3] - R[1]) / (2f * sinT)
    )
    return Pair(axis, theta)
}
