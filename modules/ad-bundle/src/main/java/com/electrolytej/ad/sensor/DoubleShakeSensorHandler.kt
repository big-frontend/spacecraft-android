package com.electrolytej.ad.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.util.Log
import com.electrolytej.sensor.ISensorHandler
import com.google.auto.service.AutoService
import getOrientation
import kotlin.math.abs

@AutoService(ISensorHandler::class)
class DoubleShakeSensorHandler : ISensorHandler {
    companion object {
        private const val TAG = "DoubleShakeSensorHandler"
    }

    private val accelerometerValues = FloatArray(3)
    private val magneticValues = FloatArray(3)

    override fun sensors(): Set<Int> {
        return setOf(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD)
    }

    /**
     * 手机水平放在桌上，xyz加速度：0,0,9.8 ; xyz角度：0,0，xx 度
     */
    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                event.values?.copyInto(accelerometerValues)
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                event.values?.copyInto(magneticValues)
            }
        }
        if (accelerometerValues.isNotEmpty()) {
            val (ax, ay, az) = accelerometerValues
//            if (abs(ax) > 10 || abs(ay) > 10 || abs(az) > 10) {
                Log.d(TAG, "xyz加速度: ${ax} ${ay} ${az}")
//            }
        }
        if (accelerometerValues.isNotEmpty() && magneticValues.isNotEmpty()) {
            val (azimuth, pitch, roll) = getOrientation(accelerometerValues, magneticValues)
            if (abs(pitch) > 35 || abs(roll) > 35 || abs(azimuth) > 35) {
//                Log.d(TAG, "xyz角度: ${pitch} ${roll} ${azimuth}")
            }
        }
        //1.摇一摇
        //2.扭一扭
        //3.反转
    }

}