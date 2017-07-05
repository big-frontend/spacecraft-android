package com.hawksjamesf.simpleweather;

import android.app.Application;

import com.orhanobut.logger.*;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/6/28
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
