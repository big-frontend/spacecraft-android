package com.jamesfchen.loader;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Debug;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;

import com.google.firebase.perf.metrics.AddTrace;
import com.jamesfchen.common.util.Util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.work.Configuration;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author:  jamesfchen
 * @since: 2017/7/4
 *
 */
@com.jamesfchen.lifecycle.App
public class App extends Application implements Configuration.Provider {
    private static App app;
    public static App getInstance() {
        if (app == null) {
            throw new IllegalStateException("app is null");
        }
        return app;
    }

    private static final String PROCESS_1 = "com.hawksjamesf.spacecraft.debug";
    private static final String PROCESS_2 = ":mock_server";
    private static final String PROCESS_3 = ":mock_jobserver";
    private long start = 0;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        app = this;
        Log.d("cjf","App#attachBaseContext");
        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            ProviderInfo[] providers = pack.providers;
            if (providers == null) continue;
            for (ProviderInfo provider : providers) {
                if ((provider.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    if (provider.packageName.equals(getPackageName())){
                        Log.d("cjf", "当前provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);
                    }else{
//                        Log.d("cjf", "第三方provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);
                    }
                }else {
//                    Log.d("cjf", "系统provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);

                }
            }
        }
        start = SystemClock.elapsedRealtime();
//        Debug.startMethodTracing(getExternalCacheDir().getParent()+"/contentprovidertrace");
        Trace.beginSection("contentprovidertrace");
    }

    /**
     * 性能优化App#attachBaseContext --> ContentProvider#onCreate--->App#onCreate
     * 一个空的ContentProvider耗时2ms
     * 2830ms优化到两位位数
     */
    @AddTrace(name = "App#onCreate",enabled = true)
    @Override
    public void onCreate() {
        Log.d("cjf","ContentProvider#onCreate消耗时间："+(SystemClock.elapsedRealtime()-start)+"ms");
//        Debug.stopMethodTracing();
        Trace.endSection();
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        Utils.init(this);
        Util.init(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
    }

    /**
     * 内存告急，释放缓存
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        memoryInfo.getTotalPss();
        memoryInfo.getTotalSwappablePss();
        int dalvikPss = memoryInfo.dalvikPss;
        int nativePss = memoryInfo.nativePss;
        int otherPss = memoryInfo.otherPss;


    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.VERBOSE)
                .build();
    }
}