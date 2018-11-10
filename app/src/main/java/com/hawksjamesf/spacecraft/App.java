package com.hawksjamesf.spacecraft;

import android.content.Context;

import com.hawksjamesf.spacecraft.data.DaggerNetComponent;
import com.hawksjamesf.spacecraft.data.NetComponent;
import com.hawksjamesf.spacecraft.data.NetModule;
import com.hawksjamesf.spacecraft.util.Util;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */

public class App extends MultiDexApplication {
    private static final String TAG = "SimpleWeatherApp---";
    private static AppComponent sAppComponent;
    private static NetComponent sNetComponent;
    private static App app;

    public static App getInstance() {
        if (app == null) {
            throw new IllegalStateException("app is null");
        }
        return app;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(TAG).build()) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        sNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();

        sAppComponent = DaggerAppComponent.builder()
                .netComponent(sNetComponent)
                .appModule(new AppModule(this))
                .build();

//        Utils.init(this);
        Util.init(this);

        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG);

//        MockManager.init(getApplicationContext());
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static NetComponent getNetComponet(){
        return sNetComponent;
    }


}