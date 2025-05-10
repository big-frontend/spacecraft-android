package com.electrolytej

import com.electrolytej.sensor.calculateAngleDifference
import org.junit.Test
import kotlin.math.max

class ShakeUnitTest {
    @Test
    fun testSensorHandler(){
        // 示例：计算两个四元数的角度差
        val q1 = floatArrayOf(0.707f, -0.707f, 0f, 0f) // 绕 X 轴旋转 90°
        val q2 = floatArrayOf(0.707f, 0f, -0.707f, 0f) // 绕 Y 轴旋转 90°
        val angleDiff = calculateAngleDifference(q1, q2) // 结果约为 120°
//        println(angleDiff)
        // 示例：计算两次旋转的 XYZ 轴角度差
        val qInitial = floatArrayOf(0.707f, 0.707f, 0f, 0f)  // 绕 X 轴旋转 90°
        val qFinal = floatArrayOf(0.707f, 0f, 0.707f, 0f)    // 绕 Y 轴旋转 90°
        // 计算 XYZ 轴的角度差
//        val (deltaX, deltaY, deltaZ) = calculateAxisAngleDiffs(qInitial, qFinal)
//        println("AngleDiffs ΔX: ${"%.1f".format(deltaX)}°, ΔY: ${"%.1f".format(deltaY)}°, ΔZ: ${"%.1f".format(deltaZ)}°")

        println(1.minus(1))
//        maxValue = max(maxValue.toDouble(), ))
//        val label = String.format("%.1f", value)
        println(Float.NaN)
    }
}