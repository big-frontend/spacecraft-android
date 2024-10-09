package com.spacecraft

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.electrolytej.main.MainActivity
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        val intent = Intent(appContext, MainActivity::class.java)
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        val activityTestRule: ActivityTestRule<MainActivity> =
//            ActivityTestRule(MainActivity::class.java, false, false)
//        activityTestRule.launchActivity(intent)
        assertEquals("com.electrolytej.spacecraft", appContext.packageName)
    }
}