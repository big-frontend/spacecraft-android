package com.electrolytej.ad.page.gesture

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LeftRightRotateEngineTest {

    @Test
    fun `no config - never triggers`() {
        val engine = LeftRightRotateEngine(configProvider = { null }, clockMs = { 0L })
        assertNull(engine.onRollDeg(0f))
        assertNull(engine.onRollDeg(100f))
    }

    @Test
    fun `right rotate triggers`() {
        var now = 0L
        val engine = LeftRightRotateEngine(
            configProvider = { LeftRightRotateConfig(rotateThresholdDeg = 30f, recoverDeg = 5f, cooldownMs = 200) },
            clockMs = { now }
        )

        now = 0L
        assertNull(engine.onRollDeg(0f)) // baseline

        now = 16L
        assertEquals(LeftRightRotateEngine.Direction.RIGHT, engine.onRollDeg(35f))

        // during cooldown, no trigger
        now = 100L
        assertNull(engine.onRollDeg(40f))

        // after cooldown, re-armed
        now = 300L
        assertNull(engine.onRollDeg(0f))
        now = 320L
        assertEquals(LeftRightRotateEngine.Direction.RIGHT, engine.onRollDeg(31f))
    }

    @Test
    fun `left rotate triggers`() {
        var now = 0L
        val engine = LeftRightRotateEngine(
            configProvider = { LeftRightRotateConfig(rotateThresholdDeg = 25f, recoverDeg = 5f, cooldownMs = 0) },
            clockMs = { now }
        )

        now = 0L
        assertNull(engine.onRollDeg(10f)) // baseline
        now = 10L
        assertEquals(LeftRightRotateEngine.Direction.LEFT, engine.onRollDeg(-20f))
    }
}
