package com.electrolytej.ad.page.gesture

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.SystemClock
import com.electrolytej.sensor.ISensorHandler
import getOrientation

class LeftRightRotateHandler(
    private val configProvider: () -> LeftRightRotateConfig? = { LeftRightRotateConfig() },
) : ISensorHandler {

    enum class Direction { LEFT, RIGHT }

    interface OnRotateListener {
        fun onRotate(direction: Direction)
    }

    var onRotateListener: OnRotateListener? = null

    private val engine = LeftRightRotateEngine(
        configProvider = configProvider,
        clockMs = { SystemClock.uptimeMillis() }
    )

    override fun sensors(): Set<Int> = setOf(Sensor.TYPE_ROTATION_VECTOR)

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type != Sensor.TYPE_ROTATION_VECTOR) return
        val values = sensorEvent.values ?: return
        val orientation = getOrientation(values)
        val rollDeg = orientation.third

        val direction = engine.onRollDeg(rollDeg.toFloat())
        if (direction != null) {
            onRotateListener?.onRotate(
                when (direction) {
                    LeftRightRotateEngine.Direction.LEFT -> Direction.LEFT
                    LeftRightRotateEngine.Direction.RIGHT -> Direction.RIGHT
                }
            )
        }
    }

    fun reset() {
        engine.reset()
    }
}