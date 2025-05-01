package com.electrolytej.sensor.filter

import android.hardware.SensorEvent

class Basic {
    // 在设备静止时校准陀螺仪零偏
    private val gyroBias = FloatArray(3)
    private var calibrationCount = 0
    private val CALIBRATION_SAMPLES = 100

    fun calibrateGyro(event: SensorEvent) {
        if (calibrationCount < CALIBRATION_SAMPLES) {
            gyroBias[0] += event.values[0]
            gyroBias[1] += event.values[1]
            gyroBias[2] += event.values[2]
            calibrationCount++

            if (calibrationCount == CALIBRATION_SAMPLES) {
                gyroBias[0] /= CALIBRATION_SAMPLES.toFloat()
                gyroBias[1] /= CALIBRATION_SAMPLES.toFloat()
                gyroBias[2] /= CALIBRATION_SAMPLES.toFloat()
            }
        }
    }

    // 使用校准数据
//    val correctedX = event.values[0] - gyroBias[0]
//    val correctedY = event.values[1] - gyroBias[1]
//    val correctedZ = event.values[2] - gyroBias[2]

    private val filteredValues = FloatArray(3)
    private val ALPHA = 0.8f

    fun applyLowPassFilter(event: SensorEvent): FloatArray {
        filteredValues[0] = ALPHA * filteredValues[0] + (1 - ALPHA) * event.values[0]
        filteredValues[1] = ALPHA * filteredValues[1] + (1 - ALPHA) * event.values[1]
        filteredValues[2] = ALPHA * filteredValues[2] + (1 - ALPHA) * event.values[2]
        return filteredValues
    }
}