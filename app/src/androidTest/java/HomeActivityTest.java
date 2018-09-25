import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.hawksjamesf.simpleweather.ui.HomeActivity;
import com.hawksjamesf.simpleweather.util.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest extends InstrumentationTestCase {

    MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockWebServer = new MockWebServer();
//        mockWebServer.url(BuildConfig.WEATHER_URL_OPEN_WEATHER_MAP);
        mockWebServer.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Constants.BASE_URL=mockWebServer.url("/").toString();
    }

    @Test
    public void testHttpIsOk() throws Exception {
        String fileName = "current_data.json";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        activityRule.launchActivity(new Intent());

//        Espresso.onView(ViewMatchers.withId(R.id.iv_day_weather)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testHttpIsError() throws Exception {
        String fileName = "404.json";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        activityRule.launchActivity(new Intent());

//        Espresso.onView(ViewMatchers.withId(R.id.iv_day_weather)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
