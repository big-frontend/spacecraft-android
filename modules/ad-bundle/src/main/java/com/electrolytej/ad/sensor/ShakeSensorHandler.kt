package com.electrolytej.ad.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import androidx.annotation.WorkerThread
import com.electrolytej.sensor.ISensorHandler
import com.electrolytej.sensor.minus
class ShakeSensorHandler : ISensorHandler {
    companion object {
        private const val TAG = "ShakeSensorHandler"
    }

    private var baseLineQuaternion: FloatArray? = null
    private val rotationValues = FloatArray(9)
    private val accelerometerValues = FloatArray(3)
    private val resultValues = DoubleArray(6)
    private var onShakeListener: OnShakeListener? = null
    override fun sensors(): Set<Int> {
        return setOf(
            Sensor.TYPE_LINEAR_ACCELERATION,
//            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_ROTATION_VECTOR,
        )
    }

    fun setOnShakeListener(onShakeListener: OnShakeListener) {
        this.onShakeListener = onShakeListener
    }

    interface OnShakeListener {
        @WorkerThread
        fun onTrace(resultValues: DoubleArray)

        @WorkerThread
        fun onShake(p: String?) {
        }
//            void onShake(float ax1, float ay1, float az1,
//                             float degreeDx1, float degreeDy1, float degreeDz1, float ax2, float ay2, float az2,
//                             float degreeDx2, float degreeDy2, float degreeDz2,long delta);
    }


    override fun onSensorChanged(sensorEvent: SensorEvent) {
        when (sensorEvent.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                sensorEvent.values?.copyInto(accelerometerValues)
                val (ax, ay, az) = accelerometerValues
                resultValues[0] = ax.toDouble()
                resultValues[1] = ay.toDouble()
                resultValues[2] = az.toDouble()
//            if (abs(ax) > 10 || abs(ay) > 10 || abs(az) > 10) {
//                Log.d(TAG, "xyz加速度: ${ax} ${ay} ${az}")
//            }
            }

            Sensor.TYPE_ROTATION_VECTOR -> {
                sensorEvent.values?.copyInto(rotationValues)
//                val (azimuth, pitch, roll) = getOrientation(rotationValues)
//                if (azimuth == null || pitch == null || roll == null) {
//                    return
//                }
//                resultValues[3] = roll
//                resultValues[4] = pitch
//                resultValues[5] = azimuth

                val quaternion = FloatArray(4)
                SensorManager.getQuaternionFromVector(quaternion, rotationValues)
                if (baseLineQuaternion == null) {
                    baseLineQuaternion = quaternion.copyOf()
                }
                baseLineQuaternion?.let {
                    val (deltaX, deltaY, deltaZ) = quaternion.minus(it)
                    resultValues[3] = deltaX
                    resultValues[4] = deltaY
                    resultValues[5] = deltaZ
//                Log.d(TAG, "旋转传感器计算出xyz角度: ${degreeX}/${degreeY}/${degreeZ}")
                }

            }
        }

        onShakeListener?.onTrace(resultValues)

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "传感器精度变化: ${sensor.name} ${accuracy}")
        if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
            // 传感器已校准
        }
    }

}