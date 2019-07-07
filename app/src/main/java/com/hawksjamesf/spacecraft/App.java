package com.hawksjamesf.spacecraft;

import android.content.Context;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.hawksjamesf.common.util.Util;
import com.hawksjamesf.mockserver.MockManager;
import com.hawksjamesf.network.DaggerNetComponent;
import com.hawksjamesf.network.NetComponent;
import com.hawksjamesf.network.NetModule;
import com.hawksjamesf.spacecraft.ui.observable.AppLifecycleObserver;
import com.hawksjamesf.spacecraft.ui.signin.SigInModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import io.fabric.sdk.android.Fabric;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */

public class App extends MultiDexApplication {
    private static final String TAG = "SpacecraftApp---";
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
                .sigInModule(new SigInModule())
                .build();

//        Utils.init(this);
        Util.init(this);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setBuglyLogUpload(true);
        String packageName = getApplicationContext().getPackageName();
        strategy.setAppPackageName(packageName);
        String processName = getProcessName(android.os.Process.myPid());
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        strategy.setAppReportDelay(20000);   //改为20s
//        strategy.setAppChannel()
//        CrashReport.setUserSceneTag(context, 9527); // 上报后的Crash会显示该标签
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), strategy);
        Fabric.with(this, new Crashlytics());
        MockManager.init(getApplicationContext(),BuildConfig.DEBUG);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
//        UETool.showUETMenu();
    }
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static NetComponent getNetComponet() {
        return sNetComponent;
    }


}