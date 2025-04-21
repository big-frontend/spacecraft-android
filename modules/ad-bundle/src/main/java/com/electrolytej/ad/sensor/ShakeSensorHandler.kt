package com.electrolytej.ad.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.Vibrator
import android.util.Log
import com.electrolytej.sensor.ISensorHandler
import com.electrolytej.util.Util
import com.google.auto.service.AutoService
import kotlin.math.sqrt

@AutoService(ISensorHandler::class)
class ShakeSensorHandler : ISensorHandler {
    companion object {
        private const val TAG = "ShakeSensorHandler"
        private const val UPDATE_INTERVAL_TIME: Int = 70 // 检测时间间隔
        private const val VELOCITY_SHRESHOLD: Int = 3000 // 速度阈值
        private const val SHAKE_INTERVAL: Int = 1000 // 两次摇动的最大时间间隔
    }

    private val accelerometerValues = FloatArray(3)
    override fun sensors() = setOf(Sensor.TYPE_ACCELEROMETER)
    override fun onSensorChanged(event: SensorEvent) {
        Log.d(TAG,"sensor:${event.sensor.type}")
        event.values?.copyInto(accelerometerValues)
        doubleShake(accelerometerValues)
    }

    private val vibrator = Util.getApp().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private var lastAX = 0f
    private var lastAY = 0f
    private var lastAZ = 0f
    private var lastUpdateTime = 0L

    // 摇动计数和时间片
    private var count = 0
    private var timeSlice = 0
    fun doubleShake(accelerometerValues: FloatArray) {
        if (accelerometerValues.isEmpty()) return
        val currentTime = System.currentTimeMillis()
        val timeInterval = currentTime - lastUpdateTime

        if (timeInterval < UPDATE_INTERVAL_TIME) {
            return
        }
        lastUpdateTime = currentTime

        val ax: Float = accelerometerValues[0]
        val ay: Float = accelerometerValues[1]
        val az: Float = accelerometerValues[2]

        val deltaX = ax - lastAX
        val deltaY = ay - lastAY
        val deltaZ = az - lastAZ
        val a = sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble())

        lastAX = ax
        lastAY = ay
        lastAZ = az
        val velocity = a * 10000 / timeInterval
        if (velocity > VELOCITY_SHRESHOLD) {
            count++
            if (count == 1) {
                timeSlice = 0
            } else if (count == 2) {
                if (timeSlice * UPDATE_INTERVAL_TIME < SHAKE_INTERVAL) {
                    // 触发双向摇一摇操作
//                    vibrator.vibrate(500)
                    Log.d(TAG, "双向摇一摇触发")
                    count = 0
                } else {
                    count = 1
                }
                timeSlice = 0
            }
        }

        if (count == 1) {
            timeSlice++
            if (timeSlice * UPDATE_INTERVAL_TIME > SHAKE_INTERVAL) {
                count = 0
                timeSlice = 0
            }
        }
    }

}