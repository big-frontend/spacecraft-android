package com.electrolytej.tool;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

//@RunWith(AndroidJUnit4.class)
public class OkHttpTest {


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
    public void testHttpIsOk() throws Exception {
//        activityRule.launchActivity(new Intent());
        /**
         * 用Dispatcher替代手动enqueue Response
         */
//        for (int i = 0; i < mockWebServer.getRequestCount(); i++) {
//            URL url = mockWebServer.takeRequest().getRequestUrl().url();
//
//            Logger.t(TAG).i("total:"+mockWebServer.getRequestCount() + "\n path:" + url.getPath());
//            switch (url.getPath()) {
//                case Constants.CURRENT_DATA_URL_PATH: {
//                    mockWebServer.enqueue(new MockResponse()
//                            .setResponseCode(200)
//                            .setBody(com.hawksjamesf.network.api.RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), Constants.CURRENT_DATA_JSON)));
//                    break;
//                }
//
//                case Constants.FIVE_DATA_URL_PATH: {
//                    mockWebServer.enqueue(new MockResponse()
//                            .setResponseCode(200)
//                            .setBody(com.hawksjamesf.network.api.RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), Constants.FIVE_DATA_JSON)));
//                    break;
//                }
//
//                default:
//                    mockWebServer.enqueue(new MockResponse()
//                            .setResponseCode(404));
//            }
//        }

//        Espresso.onView(ViewMatchers.withId(R.id.iv_day_weather)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testHttpIsError() throws Exception {
        activityRule.launchActivity(new Intent());

//        Espresso.onView(ViewMatchers.withId(R.id.iv_day_weather)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @After
    public void tearDown() throws Exception {
//        mockWebServer.shutdown();
    }
}
