package com.electrolytej.ad

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Vibrator
import android.util.Log
import kotlin.math.abs
import kotlin.math.sqrt

/**
 *
 * 三大类型 sensor
 * a.Motion sensors
 *
 * b.Environmental sensors
 *
 * c.Position sensors
 *
 * sensor 坐标轴
 * 当设备处于默认屏幕方向时，x轴从左向右水平延伸，y轴从下往上垂直延伸，z轴垂直于屏幕向外延伸
 *
 *
 */
class MySensorEventListener(context: Context) : SensorEventListener {

    companion object {
        private const val TAG = "MySensorEventListener"
        private const val UPDATE_INTERVAL_TIME: Int = 70 // 检测时间间隔
        private const val VELOCITY_SHRESHOLD: Int = 3000 // 速度阈值
        private const val SHAKE_INTERVAL: Int = 1000 // 两次摇动的最大时间间隔
    }

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private var aSensor: Sensor? = null
    private var gSensor: Sensor? = null
    private var magneticFieldSensor: Sensor? = null

    private val accelerometerValues = FloatArray(3)
    private val magneticValues = FloatArray(3)

    private var lastAX = 0f
    private var lastAY = 0f
    private var lastAZ = 0f
    private var lastUpdateTime = 0L

    // 摇动计数和时间片
    private var count = 0
    private var timeSlice = 0

    init {
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    fun start() {
        aSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
        gSensor?.let {
//            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        magneticFieldSensor?.let {
//            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    fun stop() {
        sensorManager.unregisterListener(this)

    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                event.values?.copyInto(accelerometerValues)
                doubleShake(accelerometerValues)
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                event.values?.copyInto(magneticValues)
            }
        }
        if (accelerometerValues.isNotEmpty()) {
            val (ax, ay, az) = accelerometerValues
            if (abs(ax) > 15 || abs(ay) > 15 || abs(az) > 15) {
                Log.d(TAG, "xyz加速度: ${ax} ${ay} ${az}")
            }
        }
        if (accelerometerValues.isNotEmpty() && magneticValues.isNotEmpty()) {
            val (azimuth, pitch, roll) = getOrientation(accelerometerValues, magneticValues)
            if (abs(pitch) > 35 || abs(roll) > 35 || abs(azimuth) > 35) {
                Log.d(TAG, "xyz角度: ${pitch} ${roll} ${azimuth}")
            }
        }
        //1.摇一摇
        //2.扭一扭
        //3.反转
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
    }

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

        lastAX = ax
        lastAY = ay
        lastAZ = az
        val velocity =
            sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()) * 10000 / timeInterval
        if (velocity > VELOCITY_SHRESHOLD) {
            count++
            if (count == 1) {
                timeSlice = 0
            } else if (count == 2) {
                if (timeSlice * UPDATE_INTERVAL_TIME < SHAKE_INTERVAL) {
                    // 触发双向摇一摇操作
                    vibrator.vibrate(500)
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

    /**
     * 方向角解释
     * azimuth：方位角，表示设备绕 Z 轴旋转的角度，范围是 [-π, π]。Z轴
     * pitch：俯仰角，表示设备绕 X 轴旋转的角度，范围是 [-π/2, π/2]。X轴
     * roll：横滚角，表示设备绕 Y 轴旋转的角度，范围是 [-π, π]。Y轴
     *
     */
    fun getOrientation(
        accelerometerValues: FloatArray,
        magneticValues: FloatArray
    ): Triple<Double, Double, Double> {
        //values用来存储getOrientation方法的返回值，同时R作为getOrientation方法的参数
        val orientationAngles = FloatArray(3)
        //R用来存储getRotationMatrix方法的返回值
        val rotationMatrix = FloatArray(9)
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magneticValues);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        // Convert from radians to degrees
        return Triple(
            Math.toDegrees(orientationAngles[0].toDouble()),
            Math.toDegrees(orientationAngles[1].toDouble()),
            Math.toDegrees(
                orientationAngles[2].toDouble()
            )
        )
    }
}