package com.electrolytej.sensor.filter

class KalmanFilter {
    private var Q_angle = 0.001
    private var Q_bias = 0.003
    private var R_measure = 0.03

    private var angle = 0.0
    private var bias = 0.0
    private var rate = 0.0

    private var P = Array(2) { DoubleArray(2) }

    fun update(newAngle: Double, newRate: Double, dt: Double): Double {
        // 预测阶段
        rate = newRate - bias
        angle += dt * rate

        // 更新协方差矩阵
        P[0][0] += dt * (dt * P[1][1] - P[0][1] - P[1][0] + Q_angle)
        P[0][1] -= dt * P[1][1]
        P[1][0] -= dt * P[1][1]
        P[1][1] += Q_bias * dt

        // 更新阶段
        val y = newAngle - angle
        val S = P[0][0] + R_measure
        val K = doubleArrayOf(P[0][0]/S, P[1][0]/S)

        // 更新角度和偏差
        angle += K[0] * y
        bias += K[1] * y

        // 更新协方差
        val P00_temp = P[0][0]
        val P01_temp = P[0][1]

        P[0][0] -= K[0] * P00_temp
        P[0][1] -= K[0] * P01_temp
        P[1][0] -= K[1] * P00_temp
        P[1][1] -= K[1] * P01_temp

        return angle
    }
}