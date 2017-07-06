package com.hawksjamesf.simpleweather;

import android.app.Application;

import com.orhanobut.logger.*;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */

public class SimpleWeatherApplication extends Application {
    private  static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        }

        appComponent = DaggerAppComponent.builder().build();
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
