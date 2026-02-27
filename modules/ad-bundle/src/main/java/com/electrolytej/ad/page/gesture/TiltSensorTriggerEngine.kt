package com.electrolytej.ad.page.gesture

import kotlin.math.abs

/**
 * 倾斜触发配置：
 * - rotateDeg: 单方向左右倾斜达到该角度阈值（度）
 * - recoverDeg: 回正到距离初始角度不超过该阈值（度）
 * - durationMs: 从记录初始角度开始到回正完成的总时长下限（毫秒）
 */
data class TiltSensorTriggerConfig(
    val rotateDeg: Float,
    val recoverDeg: Float,
    val durationMs: Long
)

/**
 * 纯逻辑状态机：输入 roll(左右倾斜角) 的实时值，输出是否触发。
 *
 * 规则严格按需求：
 * 记录初始角度→单方向旋转≥rotateDeg→记录峰值→回正(|delta|≤recoverDeg)→总时长≥durationMs→触发
 * 无配置时永不触发。
 * 触发后进入 STOPPED 状态，后续输入都会返回 false。
 */
internal class TiltSensorTriggerEngine(
    private val configProvider: () -> TiltSensorTriggerConfig?,
    private val clockMs: () -> Long
) {

    private var phase: Phase = Phase.IDLE

    private var startTs: Long = 0L
    private var baseRollDeg: Float = 0f

    /** 方向：+1 右倾，-1 左倾，0 未锁定 */
    private var directionSign: Int = 0

    /** 峰值（相对 base 的增量，带符号） */
    private var peakDeltaRollDeg: Float = 0f

    fun reset() {
        phase = Phase.IDLE
        startTs = 0L
        baseRollDeg = 0f
        directionSign = 0
        peakDeltaRollDeg = 0f
    }

    fun stop() {
        phase = Phase.STOPPED
    }

    fun isStopped(): Boolean = phase == Phase.STOPPED

    /**
     * @return true 表示“本次输入触发成功”（只会返回一次 true）
     */
    fun onRollDeg(currentRollDeg: Float): Boolean {
        val cfg = configProvider() ?: return false
        if (phase == Phase.STOPPED) return false

        val now = clockMs()

        if (phase == Phase.IDLE) {
            // 记录初始角度
            baseRollDeg = currentRollDeg
            startTs = now
            directionSign = 0
            peakDeltaRollDeg = 0f
            phase = Phase.ROTATING
            return false
        }

        val delta = normalizeDeltaDeg(currentRollDeg - baseRollDeg)

        // 锁定方向（避免轻微抖动导致左右摇摆）
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
                // 更新峰值
                if (directionSign != 0 && deltaInDir > abs(peakDeltaRollDeg)) {
                    peakDeltaRollDeg = delta
                }

                // 单方向旋转达到阈值
                if (directionSign != 0 && deltaInDir >= cfg.rotateDeg) {
                    // 达标时再兜一把峰值
                    if (abs(delta) >= abs(peakDeltaRollDeg)) {
                        peakDeltaRollDeg = delta
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
                        return true
                    }
                    // 时长不够：不触发，重置流程（允许用户重新来一遍）
                    phase = Phase.IDLE
                    directionSign = 0
                    peakDeltaRollDeg = 0f
                }
            }

            Phase.IDLE, Phase.STOPPED -> Unit
        }

        return false
    }

    private fun normalizeDeltaDeg(rawDelta: Float): Float {
        var d = rawDelta
        while (d > 180f) d -= 360f
        while (d < -180f) d += 360f
        return d
    }
}
