package com.hawksjamesf.simpleweather;

import com.hawksjamesf.simpleweather.data.source.DataSource;
import com.hawksjamesf.simpleweather.data.source.mock.MockDataSource;
import com.hawksjamesf.simpleweather.data.source.remote.RemoteDataSource;
import com.hawksjamesf.simpleweather.network.ObservableOrMainCallAdapterFactory;
import com.hawksjamesf.simpleweather.network.URLInterceptor;
import com.hawksjamesf.simpleweather.network.WeatherAPIInterface;
import com.hawksjamesf.simpleweather.ui.HomePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
@Module
public class AppModule {

    private SimpleWeatherApplication app;

    public AppModule(SimpleWeatherApplication app) {
        this.app = app;
    }

    @Singleton
    @Provides
    OkHttpClient provideOKHttpClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new URLInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }


    @Singleton
    @Provides
    WeatherAPIInterface provideWeatherAPIInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_URL)
//                .baseUrl("http://localhost:50195")
                .client(new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(new URLInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(new ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WeatherAPIInterface.class);
    }

    @Singleton
    @Provides
    HomePresenter provideHomePresenter() {
        return new HomePresenter();
    }

    @Provides
    @Singleton
    DataSource provideDataSource() {
        if (BuildConfig.MOCKED_DATA_ACCESS) {
            return new MockDataSource(app, new MockDataSource.UncertaintyParams(
                    0f, 0, 1500L, 500L
            ));
        } else {
            return RemoteDataSource.INSTANCE;
        }
    }
}
