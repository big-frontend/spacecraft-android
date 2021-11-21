package com.jamesfchen.bundle2.perf

import android.content.Context
import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.filters.MediumTest
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.jamesfchen.bundle1.customview.newLayoutExprimental.ConstraintPerformanceActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * UiAutomator tests that triggers measure/layouts passes in the MainActivity app to compare the
 * UI performance for ConstraintLayout in comparison to traditional layouts.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class PerformanceTest {

    private lateinit var device: UiDevice

    companion object {
        private val LAUNCH_TIMEOUT = 5000L
    }

    lateinit var context: Context
    @Before
    fun startActivityFromHomeScreen() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

//        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        context = InstrumentationRegistry.getTargetContext()
        val intent = Intent(context, ConstraintPerformanceActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)    // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)    // Clear out any previous instances

        context.startActivity(intent)
        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(context.packageName).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    @Throws(Throwable::class)
    fun testRunCalculationTraditionalLayouts() {
        runCalculation("button_start_calc_traditional")
    }

    @Test
    @Throws(Throwable::class)
    fun testRunCalculationConstraintLayout() {
        runCalculation("button_start_calc_constraint")
    }

    /**
     * Runs the calculation on a connected device or on an emulator. By clicking a button in the
     * app, the app runs measure/layout passes specific times.
     */
    private fun runCalculation(buttonIdToStart: String) {
        device.findObject(By.res(context.packageName, buttonIdToStart)).click()
        device.wait<UiObject2>(Until.findObject(By.res(context.packageName, "textview_finish")), TimeUnit.SECONDS.toMillis(15))
    }
}
