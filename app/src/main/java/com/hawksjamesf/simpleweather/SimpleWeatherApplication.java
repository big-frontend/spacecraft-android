package com.hawksjamesf.simpleweather;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/6/28
 */

public class SimpleWeatherApplication extends Application {
    private static final boolean DEBUG = true;
    private  static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        }

        appComponent = DaggerAppComponent.builder().build();

    }
}
