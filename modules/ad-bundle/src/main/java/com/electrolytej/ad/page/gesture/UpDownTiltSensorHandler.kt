package com.electrolytej.ad.page.gesture

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.SystemClock
import com.electrolytej.sensor.ISensorHandler
import getOrientation

/**
 * 上下倾斜触发 SensorHandler（接入 SensorDetector）：
 * - 监听 TYPE_ROTATION_VECTOR
 * - 用 pitch（上下倾斜 / 俯仰角）驱动触发状态机
 *
 * 触发条件（严格按需求）：
 * 记录初始角度 → 单方向旋转≥rotateDeg → 记录峰值 → 回正(|delta|≤recoverDeg) → 总时长≥durationMs → 触发并停止
 *
 * 配置来源：外部注入 configProvider；返回 null 表示“无配置，不触发”。
 */
class UpDownTiltSensorHandler(
    private val configProvider: () -> TiltSensorTriggerConfig?,
) : ISensorHandler {

    interface OnUpDownTiltTriggerListener {
        /**
         * @param direction UP=上倾触发，DOWN=下倾触发
         */
        fun onTriggered(direction: UpDownTiltSensorTriggerEngine.TriggerDirection)
    }

    var onUpDownTiltTriggerListener: OnUpDownTiltTriggerListener? = null

    /**
     * 触发后会置为 true，方便外部（Activity/Fragment）决定是否 stop SensorDetector。
     * 注意：SensorDetector 目前不支持 handler 主动注销自身，所以这里用 flag 方式传递“应停止监听”。
     */
    @Volatile
    var shouldStop: Boolean = false
        private set

    private val engine = UpDownTiltSensorTriggerEngine(
        configProvider = configProvider,
        clockMs = { SystemClock.uptimeMillis() }
    )

    override fun sensors(): Set<Int> = setOf(Sensor.TYPE_ROTATION_VECTOR)

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (shouldStop) return
        if (sensorEvent.sensor.type != Sensor.TYPE_ROTATION_VECTOR) return

        val rotationValues = sensorEvent.values ?: return
        val (_, pitchDeg, _) = getOrientation(rotationValues)

        val direction = engine.onPitchDeg(pitchDeg.toFloat())
        if (direction != null) {
            shouldStop = true
            onUpDownTiltTriggerListener?.onTriggered(direction)
        }
    }

    /**
     * 外部可主动重置（比如交互结束/重新开始交互）。
     */
    fun reset() {
        shouldStop = false
        engine.reset()
    }
}
