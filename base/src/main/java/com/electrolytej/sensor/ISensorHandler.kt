package com.electrolytej.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent

interface ISensorHandler {
    fun sensors(): Set<Int>
    fun onSensorChanged(sensorEvent: SensorEvent) {}
    fun onAccuracyChanged(sensor: Sensor, p1: Int) {}
}