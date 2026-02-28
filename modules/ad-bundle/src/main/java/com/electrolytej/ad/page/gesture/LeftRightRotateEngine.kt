package com.electrolytej.ad.page.gesture

import kotlin.math.abs

/**
 * 左右反转/翻转识别配置。
 *
 * 这里用 rollDeg（左右倾斜角）来表达“左右反转”的姿态变化：
 * - 向右翻：roll 相对基线增加到阈值
 * - 向左翻：roll 相对基线减少到阈值
 *
 * recoverDeg：回正阈值（回到接近基线）
 * cooldownMs：触发后冷却，避免连续抖动重复触发
 */
data class LeftRightRotateConfig(
    val rotateThresholdDeg: Float = 35f,
    val recoverDeg: Float = 8f,
    val cooldownMs: Long = 400L
)

internal class LeftRightRotateEngine(
    private val configProvider: () -> LeftRightRotateConfig?,
    private val clockMs: () -> Long
) {
    enum class Direction { LEFT, RIGHT }

    private enum class State { IDLE, ARMED, TRIGGERED_COOLDOWN }

    private var state: State = State.IDLE

    private var baseRollDeg: Float = 0f
    private var lastTriggerTs: Long = 0L

    fun reset() {
        state = State.IDLE
        baseRollDeg = 0f
        lastTriggerTs = 0L
    }

    /**
     * @return 触发方向；未触发返回 null
     */
    fun onRollDeg(rollDeg: Float): Direction? {
        val cfg = configProvider() ?: return null
        val now = clockMs()

        when (state) {
            State.IDLE -> {
                baseRollDeg = rollDeg
                state = State.ARMED
                return null
            }

            State.TRIGGERED_COOLDOWN -> {
                if (now - lastTriggerTs >= cfg.cooldownMs) {
                    // 冷却结束，重新建立基线
                    baseRollDeg = rollDeg
                    state = State.ARMED
                }
                return null
            }

            State.ARMED -> {
                val delta = normalizeDeltaDeg(rollDeg - baseRollDeg)

                // 达到阈值 => 触发
                if (delta >= cfg.rotateThresholdDeg) {
                    state = State.TRIGGERED_COOLDOWN
                    lastTriggerTs = now
                    return Direction.RIGHT
                }
                if (delta <= -cfg.rotateThresholdDeg) {
                    state = State.TRIGGERED_COOLDOWN
                    lastTriggerTs = now
                    return Direction.LEFT
                }

                // 若用户回正到基线附近，则更新基线（减少漂移+抖动）
                if (abs(delta) <= cfg.recoverDeg) {
                    baseRollDeg = rollDeg
                }
                return null
            }
        }
    }

    private fun normalizeDeltaDeg(rawDelta: Float): Float {
        var d = rawDelta
        while (d > 180f) d -= 360f
        while (d < -180f) d += 360f
        return d
    }
}
