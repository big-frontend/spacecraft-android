import androidx.test.filters.SmallTest;
import android.test.InstrumentationTestCase;

import com.hawksjamesf.simpleweather.data.bean.home.WeatherData;
import com.hawksjamesf.simpleweather.data.source.remote.WeatherAPIInterface;

import junit.framework.Assert;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
//@RunWith(AndroidJUnit4.class)
public class RetrofitTest extends InstrumentationTestCase {

    MockRetrofit mockRetrofit;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test.com")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkBehavior networkBehavior = NetworkBehavior.create();
        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior)
                .build();

    }

    @SmallTest
    public void testHttpIsOk() {
        BehaviorDelegate<WeatherAPIInterface> delegate = mockRetrofit.create(WeatherAPIInterface.class);
        HttpIsOkApi mockApi = new HttpIsOkApi(getInstrumentation().getContext(), delegate);
        mockApi.getCurrentWeatherDate("Shanghai")
                .subscribe(new Consumer<Response<WeatherData>>() {
                    @Override
                    public void accept(Response<WeatherData> response) throws Exception {
                        Assert.assertTrue(response.isSuccessful());
                    }
                });
    }

}
