package com.electrolytej.ad.page.gesture

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TiltSensorTriggerEngineTest {

    @Test
    fun `no config - never triggers`() {
        val engine = TiltSensorTriggerEngine(
            configProvider = { null },
            clockMs = { 0L }
        )
        assertFalse(engine.onRollDeg(0f))
        assertFalse(engine.onRollDeg(30f))
    }

    @Test
    fun `rotate then recover then duration ok - triggers and stops`() {
        var now = 0L
        val engine = TiltSensorTriggerEngine(
            configProvider = { TiltSensorTriggerConfig(rotateDeg = 20f, recoverDeg = 5f, durationMs = 500L) },
            clockMs = { now }
        )

        // initial
        now = 0L
        assertFalse(engine.onRollDeg(0f))

        // rotate to the right beyond threshold
        now = 100L
        assertFalse(engine.onRollDeg(25f))

        // keep peak
        now = 200L
        assertFalse(engine.onRollDeg(30f))

        // recover close to base, but duration not enough yet -> should reset (no trigger)
        now = 300L
        assertFalse(engine.onRollDeg(2f))

        // start again
        now = 400L
        assertFalse(engine.onRollDeg(0f))
        now = 600L
        assertFalse(engine.onRollDeg(25f))
        now = 950L
        // recovered + duration ok => trigger
        assertTrue(engine.onRollDeg(2f))

        // after trigger, further updates should be ignored
        now = 1100L
        assertFalse(engine.onRollDeg(30f))
    }

    @Test
    fun `left direction rotation also works`() {
        var now = 0L
        val engine = TiltSensorTriggerEngine(
            configProvider = { TiltSensorTriggerConfig(rotateDeg = 15f, recoverDeg = 4f, durationMs = 200L) },
            clockMs = { now }
        )

        now = 0L
        assertFalse(engine.onRollDeg(0f))

        // rotate left
        now = 50L
        assertFalse(engine.onRollDeg(-18f))

        // recover
        now = 250L
        assertTrue(engine.onRollDeg(-1f))
    }
}
