package com.electrolytej.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.util.ServiceLoader

class SensorDispatcher(context: Context) : SensorEventListener {
    companion object {
        private const val TAG = "SensorDispatcher"
    }

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var aSensor: Sensor? = null//加速度传感器  加速度传感器 = 重力传感器 + 线性加速度传感器
    private var linearASensor: Sensor? = null//线性加速度传感器
    private var gSensor: Sensor? = null//陀螺仪
    private var magneticFieldSensor: Sensor? = null//磁场传感器，
    private var gravitySensor: Sensor? = null//重力传感器
    private val sensors = mutableMapOf<Int, Sensor?>()
    private val sensorHandler = mutableMapOf<Class<*>, ISensorHandler>()

    init {
        val serviceLoader: ServiceLoader<ISensorHandler> = ServiceLoader.load(ISensorHandler::class.java,Thread.currentThread().contextClassLoader)

        for (h in serviceLoader){
            Log.d(TAG," ${h.javaClass}")
            sensorHandler[h.javaClass] = h
        }
        for ((k, handler) in sensorHandler) {
            for (s in handler.sensors()) {
                if (!sensors.containsKey(s)) {
                    sensors[s] = sensorManager.getDefaultSensor(s)
                }
            }
        }
        Log.d(TAG, "sensors:${sensors.keys}")
    }

    fun start() {
        for ((k, s) in sensors) {
            s?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)

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
}