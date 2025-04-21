package com.electrolytej.ad.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.electrolytej.sensor.ISensorHandler
import com.google.auto.service.AutoService
import kotlin.math.abs
import kotlin.math.sqrt
@AutoService(ISensorHandler::class)
class TwoStageShakeSensorHandler : ISensorHandler {
    companion object {
        private const val TAG = "TwoStageShakeSensorHandler"
        // 配置值
        private const val ANGLE_THRESHOLD: Float = 50f // 角度相差阈值（50度）
        private const val SPEED_THRESHOLD: Float = 10f // 速度阈值
    }

    // 初始对照值
    private val initialOrientation = FloatArray(3) // 初始方向（方位角、俯仰角、横滚角）
    private val initialGravity = FloatArray(3) // 初始重力加速度

    // 当前值
    private val currentOrientation = FloatArray(3) // 当前方向
    private val currentGravity = FloatArray(3) // 当前重力加速度
    private val angularVelocity = FloatArray(3) // 当前角速度

    override fun sensors() = setOf(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GYROSCOPE)

    // 回调接口
    interface OnTriggerListener {
        fun onTrigger()
    }

    private val listener: OnTriggerListener? = null


    override fun onSensorChanged(sensorEvent: SensorEvent) {
        when (sensorEvent.sensor.type) {
            Sensor.TYPE_ACCELEROMETER ->                 // 更新重力加速度数据
                System.arraycopy(sensorEvent.values, 0, currentGravity, 0, sensorEvent.values.size)

            Sensor.TYPE_GYROSCOPE ->                 // 更新角速度数据
                System.arraycopy(sensorEvent.values, 0, angularVelocity, 0, sensorEvent.values.size)
        }


        // 计算当前方向
        calculateOrientation()


        // 触发逻辑
        checkTriggerConditions()
    }

    // 计算当前方向
    private fun calculateOrientation() {
        // 这里假设已经通过加速度传感器和磁力计计算了方向
        // 实际实现中需要使用 SensorManager.getRotationMatrix() 和 SensorManager.getOrientation()
        // 以下为伪代码
        val rotationMatrix = FloatArray(9)
        if (SensorManager.getRotationMatrix(rotationMatrix, null, currentGravity, FloatArray(3))) {
            SensorManager.getOrientation(rotationMatrix, currentOrientation)
        }
    }

    // 检查触发条件
    private fun checkTriggerConditions() {
        // 第一阶段：记录初始值
        if (initialOrientation[0] == 0f && initialOrientation[1] == 0f && initialOrientation[2] == 0f) {
            System.arraycopy(currentOrientation, 0, initialOrientation, 0, 3)
            System.arraycopy(currentGravity, 0, initialGravity, 0, 3)
            return
        }

        // 第二阶段：检查条件
        val isOrientationMatched = checkOrientationMatch()
        val isSpeedMatched = checkSpeedMatch()

        if (isOrientationMatched && isSpeedMatched) {
            // 触发跳转
            listener?.onTrigger()
        }
    }

    // 检查方向是否匹配
    private fun checkOrientationMatch(): Boolean {
        // 计算当前方向与初始方向的差值
        val deltaAzimuth = abs(currentOrientation[0] - initialOrientation[0])
        val deltaPitch = abs(currentOrientation[1] - initialOrientation[1])
        val deltaRoll = abs(currentOrientation[2] - initialOrientation[2])

        // 判断是否有两个方向差值小于阈值
        var matchCount = 0
        if (deltaAzimuth < ANGLE_THRESHOLD) matchCount++
        if (deltaPitch < ANGLE_THRESHOLD) matchCount++
        if (deltaRoll < ANGLE_THRESHOLD) matchCount++

        return matchCount >= 2
    }

    // 检查速度是否匹配
    private fun checkSpeedMatch(): Boolean {
        // 计算当前速度
        return sqrt(
            angularVelocity[0] * angularVelocity[0] + angularVelocity[1] * angularVelocity[1] + angularVelocity[2] * angularVelocity[2]
        ) >= SPEED_THRESHOLD
    }
}