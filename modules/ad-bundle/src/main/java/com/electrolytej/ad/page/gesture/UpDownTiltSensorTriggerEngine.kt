package com.electrolytej.ad.page.gesture

import kotlin.math.abs

/**
 * 倾斜触发配置：
 * - rotateDeg: 单方向“上下倾斜”达到该角度阈值（度）
 * - recoverDeg: 回正到距离初始角度不超过该阈值（度）
 * - durationMs: 从记录初始角度开始到回正完成的总时长下限（毫秒）
 */
data class TiltSensorTriggerConfig(
    val rotateDeg: Float,
    val recoverDeg: Float,
    val durationMs: Long
)

/**
 * 纯逻辑状态机：输入 pitch(上下倾斜角/俯仰角) 的实时值，输出是否触发。
 *
 * 规则严格按需求：
 * 记录初始角度→单方向旋转≥rotateDeg→记录峰值→回正(|delta|≤recoverDeg)→总时长≥durationMs→触发
 * 无配置时永不触发。
 * 触发后进入 STOPPED 状态，后续输入都会返回 false。
 */
class UpDownTiltSensorTriggerEngine(
    private val configProvider: () -> TiltSensorTriggerConfig?,
    private val clockMs: () -> Long
) {

    enum class TriggerDirection { UP, DOWN }

    private var phase: Phase = Phase.IDLE

    private var startTs: Long = 0L
    private var basePitchDeg: Float = 0f

    /** 方向：+1 上倾（pitch 增大），-1 下倾（pitch 减小），0 未锁定 */
    private var directionSign: Int = 0

    /** 峰值（相对 base 的增量，带符号） */
    private var peakDeltaPitchDeg: Float = 0f

    fun reset() {
        phase = Phase.IDLE
        startTs = 0L
        basePitchDeg = 0f
        directionSign = 0
        peakDeltaPitchDeg = 0f
    }

    fun stop() {
        phase = Phase.STOPPED
    }

    fun isStopped(): Boolean = phase == Phase.STOPPED

    /**
     * @return 触发方向；未触发返回 null（只会返回一次非空，之后进入 STOPPED）
     */
    fun onPitchDeg(currentPitchDeg: Float): TriggerDirection? {
        val cfg = configProvider() ?: return null
        if (phase == Phase.STOPPED) return null

        val now = clockMs()

        if (phase == Phase.IDLE) {
            // 记录初始角度
            basePitchDeg = currentPitchDeg
            startTs = now
            directionSign = 0
            peakDeltaPitchDeg = 0f
            phase = Phase.ROTATING
            return null
        }

        val delta = normalizeDeltaDeg(currentPitchDeg - basePitchDeg)

        // 锁定方向（避免轻微抖动导致上下摇摆）
        if (directionSign == 0) {
            val deadZone = 1.0f
            directionSign = when {
                delta > deadZone -> 1
                delta < -deadZone -> -1
                else -> 0
            }
        }

        val deltaInDir = if (directionSign == 0) delta else delta * directionSign

        when (phase) {
            Phase.ROTATING -> {
                // 更新峰值（在“当前锁定方向”下比较幅度）
                // peakDeltaPitchDeg 是带符号的 delta，所以这里要用 abs(peakDeltaPitchDeg) 作为已记录峰值幅度。
                if (directionSign != 0 && deltaInDir > abs(peakDeltaPitchDeg)) {
                    peakDeltaPitchDeg = delta
                }

                // 单方向旋转达到阈值
                if (directionSign != 0 && deltaInDir >= cfg.rotateDeg) {
                    // 达标时再兜一把峰值
                    if (abs(delta) >= abs(peakDeltaPitchDeg)) {
                        peakDeltaPitchDeg = delta
                    }
                    phase = Phase.PEAK_RECORDED
                }
            }

            Phase.PEAK_RECORDED -> {
                // 回正判定：回到 base 附近
                val recovered = abs(delta) <= cfg.recoverDeg
                if (recovered) {
                    val durationOk = now - startTs >= cfg.durationMs
                    if (durationOk) {
                        phase = Phase.STOPPED
                        return when (directionSign) {
                            1 -> TriggerDirection.UP
                            -1 -> TriggerDirection.DOWN
                            else -> null
                        }
                    }
                    // 时长不够：不触发，重置流程（允许用户重新来一遍）
                    phase = Phase.IDLE
                    directionSign = 0
                    peakDeltaPitchDeg = 0f
                }
            }

            Phase.IDLE, Phase.STOPPED -> Unit
        }

        return null
    }

    private fun normalizeDeltaDeg(rawDelta: Float): Float {
        var d = rawDelta
        while (d > 180f) d -= 360f
        while (d < -180f) d += 360f
        return d
    }
}
