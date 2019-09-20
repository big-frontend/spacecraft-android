package com.hawksjamesf.spacecraft;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import com.hawksjamesf.mockserver.DispatcherImpl;
import com.hawksjamesf.mockserver.RestServiceTestHelper;
import com.hawksjamesf.spacecraft.ui.home.HomeActivity;
import com.orhanobut.logger.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
//@RunWith(AndroidJUnit4.class)
public class OkHttpTest  {

    public static final String TAG = "com.hawksjamesf.spacecraft.OkHttpTest";
    MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class, true, false);
    DispatcherImpl dispatcher;
    Instrumentation instrumentation;
    Context context;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
//        mockWebServer.start();
        mockWebServer.start(50195);
        mockWebServer.url("/");
        Logger.t(TAG).i(mockWebServer.url("/").toString());
//        mockWebServer.url(BuildConfig.WEATHER_URL_OPEN_WEATHER_MAP);
//        mockWebServer.url("/data/2.5/weather");
         instrumentation = InstrumentationRegistry.getInstrumentation();
         context = instrumentation.getContext();
//        injectInstrumentation(instrumentation);
        dispatcher = DispatcherImpl.getInstance(context);
        mockWebServer.setDispatcher(dispatcher);
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
        String fileName = "404.json";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(RestServiceTestHelper.getStringFromFile(context, fileName)));

        activityRule.launchActivity(new Intent());

//        Espresso.onView(ViewMatchers.withId(R.id.iv_day_weather)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @After
    public void tearDown() throws Exception {
//        mockWebServer.shutdown();
    }
}
