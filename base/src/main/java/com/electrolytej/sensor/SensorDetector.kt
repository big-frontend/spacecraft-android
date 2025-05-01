package com.electrolytej.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.collection.arrayMapOf
import com.blankj.utilcode.util.ThreadUtils
import com.electrolytej.av.AvRecorderService.WorkHandler
import java.util.ServiceLoader

class SensorDetector(context: Context) : SensorEventListener {
    companion object {
        private const val TAG = "SensorDetector"
    }

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var aSensor: Sensor? = null//加速度传感器  加速度传感器 = 重力传感器 + 线性加速度传感器
    private var linearASensor: Sensor? = null//线性加速度传感器
    private var gSensor: Sensor? = null//陀螺仪
    private var magneticFieldSensor: Sensor? = null//磁场传感器，
    private var gravitySensor: Sensor? = null
    private val sensors = mutableMapOf<Int, Sensor>()
    private val sensorHandler = arrayMapOf<Class<*>, ISensorHandler>()
    var samplingPeriodUs = SensorManager.SENSOR_DELAY_NORMAL
    private val handlerThread = HandlerThread("handler_thread")
    private var workHandler: WorkHandler
    var isStop = false
    init {
        handlerThread.start()
        workHandler = WorkHandler(handlerThread.looper)
    }
    inner class WorkHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
        }
    }

    fun start() {
        isStop = false

        for ((k, handler) in sensorHandler) {
            for (sensorType in handler.sensors()) {
                if (!sensors.containsKey(sensorType)) {
                    val s = sensorManager.getDefaultSensor(sensorType)
                    if (s == null) {
                        Log.d(TAG, "Sensor not supported: $sensorType")
                        continue
                    }
                    sensors[sensorType] = s
                    sensorManager.registerListener(this, s, samplingPeriodUs)
//                    sensorManager.registerListener(this, s, samplingPeriodUs,workHandler)
                }
            }
        }
    }

    fun stop() {
        isStop = true
        sensors.clear()
        sensorManager.unregisterListener(this)
        sensorManager.flush(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        for ((k, handler) in sensorHandler) {
            if (handler.sensors().contains(event.sensor.type)) {
                handler.onSensorChanged(event)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, p1: Int) {
        for ((k, handler) in sensorHandler) {
            if (handler.sensors().contains(sensor.type)) {
                handler.onAccuracyChanged(sensor, p1)
            }
        }
    }
    fun addHandler(handler: ISensorHandler) {
        sensorHandler[handler.javaClass] = handler
    }
}