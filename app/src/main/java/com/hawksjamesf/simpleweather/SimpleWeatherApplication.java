package com.hawksjamesf.simpleweather;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */

public class SimpleWeatherApplication extends Application {
    private static final String TAG ="SimpleWeatherApp---";
    private  static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(TAG).build()){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        appComponent = DaggerAppComponent.builder().build();

        CrashReport.initCrashReport(getApplicationContext(), "a5f4e5063e", false);
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
