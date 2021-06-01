package com.jamesfchen.storage;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
//@RunWith(AndroidJUnit4.class)
public class LogDBTest {

    public static final String TAG = "com.hawksjamesf.spacecraft.OkHttpTest";

    @Rule
    public ActivityTestRule<LogDBActivity> activityRule = new ActivityTestRule<>(LogDBActivity.class, true, false);
    Instrumentation instrumentation;
    Context context;

    @Before
    public void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        context = instrumentation.getContext();
//        injectInstrumentation(instrumentation);
    }


    @Test
    public void testHttpIsError() throws Exception {
        activityRule.launchActivity(new Intent());

//        Espresso.onView(ViewMatchers.withId(123)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @After
    public void tearDown() throws Exception {
    }
}
