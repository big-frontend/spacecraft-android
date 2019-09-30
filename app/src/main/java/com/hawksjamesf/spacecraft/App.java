package com.hawksjamesf.spacecraft;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hawksjamesf.common.util.Util;
import com.hawksjamesf.network.DaggerNetComponent;
import com.hawksjamesf.network.NetComponent;
import com.hawksjamesf.network.NetModule;
import com.hawksjamesf.spacecraft.ui.observable.AppLifecycleObserver;
import com.hawksjamesf.spacecraft.ui.signin.SigInModule;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import androidx.annotation.NonNull;
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
    protected static final String TAG = "SpacecraftApp---";
    private static AppComponent sAppComponent;
    private static NetComponent sNetComponent;
    private static App app;
    private static FirebaseRemoteConfig sFirebaseRemoteConfig;

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
//        MockManager.init(getApplicationContext(),BuildConfig.DEBUG);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());


        sFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .setFetchTimeoutInSeconds(60 * 60)
                .build();
        sFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        sFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        sFirebaseRemoteConfig.ensureInitialized();
        sFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        Log.i(TAG, "onComplete-perf_enable:" + sFirebaseRemoteConfig.getBoolean("perf_enable"));
                        FirebasePerformance.getInstance().setPerformanceCollectionEnabled(sFirebaseRemoteConfig.getBoolean("perf_enable"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.i(TAG, "onSuccess:" + aBoolean);
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.i(TAG, "onCanceled");
                    }
                });
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

    public static FirebaseRemoteConfig getFirebaseRemoteConfig() {
        return sFirebaseRemoteConfig;
    }


}