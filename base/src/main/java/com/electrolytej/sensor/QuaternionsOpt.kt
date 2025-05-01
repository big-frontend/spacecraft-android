package com.electrolytej.sensor

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.withSign

fun quaternionToEulerAngles(q: FloatArray): Triple<Double, Double, Double> {
    val w = q[0]
    val x = q[1]
    val y = q[2]
    val z = q[3]

    // 计算方位角（Azimuth/Yaw）
    val sinYaw = 2.0 * (w * z + x * y)
    val cosYaw = 1.0 - 2.0 * (y * y + z * z)
    val azimuth = atan2(sinYaw, cosYaw)

    // 计算俯仰角（Pitch）
    val sinPitch = 2.0 * (w * y - z * x)
    val pitch = if (abs(sinPitch) >= 1) {
        (Math.PI / 2).withSign(sinPitch) // 处理 ±90° 的极端值
    } else {
        asin(sinPitch)
    }
    // 计算横滚角（Roll）
    val sinRoll = 2.0 * (w * x + y * z)
    val cosRoll = 1.0 - 2.0 * (x * x + y * y)
    val roll = atan2(sinRoll, cosRoll)
    return Triple(
        Math.toDegrees(azimuth), Math.toDegrees(pitch), Math.toDegrees(roll)
    )
}

/**
 * 四元数插值计算
 */
fun slerp(q1: FloatArray, q2: FloatArray, t: Float): FloatArray {
    var dot = q1[0] * q2[0] + q1[1] * q2[1] + q1[2] * q2[2] + q1[3] * q2[3]
    if (dot < 0) {
        dot = -dot
        q2[0] = -q2[0]; q2[1] = -q2[1]; q2[2] = -q2[2]; q2[3] = -q2[3]
    }

    val theta = acos(dot.toDouble()).toFloat()
    val sinTheta = sin(theta)

    return if (sinTheta > 0.001f) {
        val ratioA = sin((1 - t) * theta) / sinTheta
        val ratioB = sin(t * theta) / sinTheta
        floatArrayOf(
            ratioA * q1[0] + ratioB * q2[0],
            ratioA * q1[1] + ratioB * q2[1],
            ratioA * q1[2] + ratioB * q2[2],
            ratioA * q1[3] + ratioB * q2[3]
        )
    } else {
        // 线性插值
        floatArrayOf(
            q1[0] + t * (q2[0] - q1[0]),
            q1[1] + t * (q2[1] - q1[1]),
            q1[2] + t * (q2[2] - q1[2]),
            q1[3] + t * (q2[3] - q1[3])
        )
    }
}

/**
 * 确保输入四元数已归一化（单位长度）
 */
fun normalizeQuaternion(q: FloatArray): FloatArray {
    val norm = sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3])
    return if (norm > 1e-6f) {
        floatArrayOf(q[0] / norm, q[1] / norm, q[2] / norm, q[3] / norm)
    } else {
        floatArrayOf(1f, 0f, 0f, 0f) // 默认单位四元数
    }
}

/**
 * 直接使用四元数进行旋转运算（如插值、叠加旋转)
 * 计算相对旋转四元数：通过四元数乘法计算从 q1 到 q2 的差异四元数 q_diff
 */
fun multiplyQuaternions(q1: FloatArray, q2: FloatArray): FloatArray {
    val w = q1[0] * q2[0] - q1[1] * q2[1] - q1[2] * q2[2] - q1[3] * q2[3]
    val x = q1[0] * q2[1] + q1[1] * q2[0] + q1[2] * q2[3] - q1[3] * q2[2]
    val y = q1[0] * q2[2] - q1[1] * q2[3] + q1[2] * q2[0] + q1[3] * q2[1]
    val z = q1[0] * q2[3] + q1[1] * q2[2] - q1[2] * q2[1] + q1[3] * q2[0]
    return floatArrayOf(w, x, y, z)
}

/**
 *通过四元数的实部计算角度差（范围 [0°, 180°]）
 */
fun calculateAngleDifference(q1: FloatArray, q2: FloatArray): Double {
    // 归一化输入四元数
    val q1Norm = normalizeQuaternion(q1)
    val q2Norm = normalizeQuaternion(q2)

    // 计算相对旋转四元数
    val qDiff = multiplyQuaternions(q2Norm, getConjugate(q1Norm))

    // 计算角度差（弧度）
    val absW = abs(qDiff[0]).coerceAtMost(1.0f)
    val thetaRadians = 2 * acos(absW)

    return Math.toDegrees(thetaRadians.toDouble())
}

fun getConjugate(q: FloatArray): FloatArray {
    return floatArrayOf(q[0], -q[1], -q[2], -q[3])
}

// 四元数 → 旋转矩阵 → 欧拉角（ZYX 顺序）
fun quaternionToEulerZYX(q: FloatArray): Triple<Double, Double, Double> {
    val (w, x, y, z) = q

    // 计算旋转矩阵
    val m = arrayOf(
        floatArrayOf(1 - 2 * y * y - 2 * z * z, 2 * x * y - 2 * z * w, 2 * x * z + 2 * y * w),
        floatArrayOf(2 * x * y + 2 * z * w, 1 - 2 * x * x - 2 * z * z, 2 * y * z - 2 * x * w),
        floatArrayOf(2 * x * z - 2 * y * w, 2 * y * z + 2 * x * w, 1 - 2 * x * x - 2 * y * y)
    )

    // 计算欧拉角（Z-Y-X 顺序）
    val yaw = atan2(m[0][1].toDouble(), m[0][0].toDouble())   // Z 轴（方位角）
    val pitch = asin((-m[0][2]).toDouble())          // Y 轴（俯仰角）
    val roll = atan2(m[1][2].toDouble(), m[2][2].toDouble())  // X 轴（横滚角）

    return Triple(
        Math.toDegrees(yaw), Math.toDegrees(pitch), Math.toDegrees(roll)
    )
}

// 计算各轴角度差
fun FloatArray.minus(q: FloatArray): Triple<Double, Double, Double> {
    val q1 = q
    val q2 = this
    //q2-q1
    //函数的计算结果 表示从 q1 到 q2 的旋转差异（即 q2 相对于 q1 的旋转角度差)
    // 计算相对旋转四元数（从 q1 到 q2）
    val q1Conj = floatArrayOf(q1[0], -q1[1], -q1[2], -q1[3]) // 四元数共轭（逆）
    val qDiff = multiplyQuaternions(q1Conj, q2)
    val (deltaYaw, deltaPitch, deltaRoll) = quaternionToEulerZYX(qDiff)
    return Triple(deltaRoll, deltaPitch, deltaYaw) // 返回 X/Y/Z 轴角度差
}