package com.electrolytej.ad.page.gesture

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TiltSensorTriggerEngineTest {

    @Test
    fun `no config - never triggers`() {
        val engine = UpDownTiltSensorTriggerEngine(
            configProvider = { null },
            clockMs = { 0L }
        )
        assertNull(engine.onPitchDeg(0f))
        assertNull(engine.onPitchDeg(30f))
    }

    @Test
    fun `up tilt - rotate then recover then duration ok triggers`() {
        var now = 0L
        val engine = UpDownTiltSensorTriggerEngine(
            configProvider = { TiltSensorTriggerConfig(rotateDeg = 20f, recoverDeg = 5f, durationMs = 500L) },
            clockMs = { now }
        )

        now = 0L
        assertNull(engine.onPitchDeg(10f)) // baseline

        now = 100L
        assertNull(engine.onPitchDeg(35f)) // delta +25 >= 20

        now = 600L
        assertEquals(UpDownTiltSensorTriggerEngine.TriggerDirection.UP, engine.onPitchDeg(12f)) // recovered |delta|<=5 and duration ok

        // after trigger, further updates should be ignored
        now = 800L
        assertNull(engine.onPitchDeg(40f))
    }

    @Test
    fun `down tilt - rotate then recover triggers`() {
        var now = 0L
        val engine = UpDownTiltSensorTriggerEngine(
            configProvider = { TiltSensorTriggerConfig(rotateDeg = 15f, recoverDeg = 4f, durationMs = 0L) },
            clockMs = { now }
        )

        now = 0L
        assertNull(engine.onPitchDeg(0f))

        now = 10L
        assertNull(engine.onPitchDeg(-20f)) // down tilt

        now = 20L
        assertEquals(UpDownTiltSensorTriggerEngine.TriggerDirection.DOWN, engine.onPitchDeg(-2f)) // recover
    }

    @Test
    fun `duration too short - recover resets and does not trigger`() {
        var now = 0L
        val engine = UpDownTiltSensorTriggerEngine(
            configProvider = { TiltSensorTriggerConfig(rotateDeg = 20f, recoverDeg = 5f, durationMs = 1000L) },
            clockMs = { now }
        )

        now = 0L
        assertNull(engine.onPitchDeg(0f))

        now = 100L
        assertNull(engine.onPitchDeg(25f))

        // recover but duration not ok => should reset and return null
        now = 600L
        assertNull(engine.onPitchDeg(2f))

        // next call should re-baseline
        now = 700L
        assertNull(engine.onPitchDeg(2f))
    }
}
