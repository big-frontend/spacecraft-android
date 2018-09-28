import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.hawksjamesf.mockserver.Constants;
import com.hawksjamesf.mockserver.RestServiceTestHelper;
import com.hawksjamesf.simpleweather.ui.HomeActivity;
import com.orhanobut.logger.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

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
public class OkHttpTest extends InstrumentationTestCase {

    public static final String TAG = "OkHttpTest";
    MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockWebServer = new MockWebServer();
//        mockWebServer.start();
        mockWebServer.start(50195);
        mockWebServer.url("/");
        Logger.t(TAG).i(mockWebServer.url("/").toString());
//        mockWebServer.url(BuildConfig.WEATHER_URL_OPEN_WEATHER_MAP);
//        mockWebServer.url("/data/2.5/weather");
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
//        mockWebServer.setDispatcher(new Dispatcher() {
//            @Override
//            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
//                URL url = request.getRequestUrl().url();
//                String fileName;
//                switch (url.getPath()) {
//                    case Constants.CURRENT_DATA_URL_PATH: {
//                        fileName = Constants.CURRENT_DATA_JSON;
//                        break;
//                    }
//
//                    case Constants.FIVE_DATA_URL_PATH: {
//                        fileName = Constants.FIVE_DATA_JSON;
//                        break;
//                    }
//
//                    default:
//                        Logger.t(TAG).e(url.getPath());
//                        return new MockResponse()
//                                .setResponseCode(404);
//                }
//                Logger.t(TAG).d(fileName);
//                String stringFromFile = "";
//                try {
//                    stringFromFile = RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return new MockResponse()
//                        .setResponseCode(200)
//                        .setBody(stringFromFile);
//            }
//        });
    }

    @Test
    public void testHttpIsOk() throws Exception {
        activityRule.launchActivity(new Intent());
        for (int i = 0; i < mockWebServer.getRequestCount(); i++) {
            URL url = mockWebServer.takeRequest().getRequestUrl().url();

            Logger.t(TAG).i("total:"+mockWebServer.getRequestCount() + "\n path:" + url.getPath());
            switch (url.getPath()) {
                case Constants.CURRENT_DATA_URL_PATH: {
                    mockWebServer.enqueue(new MockResponse()
                            .setResponseCode(200)
                            .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), Constants.CURRENT_DATA_JSON)));
                    break;
                }

                case Constants.FIVE_DATA_URL_PATH: {
                    mockWebServer.enqueue(new MockResponse()
                            .setResponseCode(200)
                            .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), Constants.FIVE_DATA_JSON)));
                    break;
                }

                default:
                    mockWebServer.enqueue(new MockResponse()
                            .setResponseCode(404));
            }
        }

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
//        mockWebServer.shutdown();
    }
}
