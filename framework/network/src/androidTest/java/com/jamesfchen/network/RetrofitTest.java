package com.jamesfchen.network;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.Before;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
@RunWith(AndroidJUnit4.class)
public class RetrofitTest  {

    MockRetrofit mockRetrofit;
    Instrumentation instrumentation;
    Context context;
    @Before
    protected void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        context = instrumentation.getContext();
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
//        BehaviorDelegate<WeatherApi> delegate = mockRetrofit.create(WeatherApi.class);
//        WeatherApiImpl mockApi = new WeatherApiImpl(context, delegate);
//        mockApi.getCurrentWeatherDate("Shanghai")
//                .subscribe(new Consumer<Response<WeatherData>>() {
//                    @Override
//                    public void accept(Response<WeatherData> response) throws Exception {
//                        Assert.assertTrue(response.isSuccessful());
//                    }
//                });
    }

}
