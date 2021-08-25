package com.jamesfchen.loader;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
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
 * App启动分为冷启动、热启动
 * 冷启动：
 *  - 基础组件 amp bundle av image storage network 必须放在主线程初始
 *  - 其他组件 map 可以异步初始化
 * 热启动：
 *
 * App#attachBaseContext --> ContentProvider#attachInfo --> ContentProvider#onCreate--->App#onCreate
 *
 * Android团队提供的startup库，主要是在ContentProvider#onContext初始化各个Initializer
 * ps: 关于startup库实现的吐槽点，用ContentProvider去做初始化，感觉没啥意义，多了一次跨进程创建ContentProvider(空ContentProvider耗时2ms)，
 * 对于追求极致的启动速度，不应该减少这个操作嘛。为什么就不放在Application这个类中做，我觉得可以把InitializationProvider的代码整合到Application中。
 *
 * 总之一句话startup库就是垃圾，应该解决的是各个启动项谁可以同步初始化，谁可以异步初始化。fast start up.
 *
 *
 * matrix：
 *  * firstMethod.i       LAUNCH_ACTIVITY   onWindowFocusChange   LAUNCH_ACTIVITY    onWindowFocusChange
 *  * ^                         ^                   ^                     ^                  ^
 *  * |                         |                   |                     |                  |
 *  * |---------app---------|---|---firstActivity---|---------...---------|---careActivity---|
 *  * |<--applicationCost-->|
 *  * |<----firstScreenCost---->|
 *  * |<---------------------------allCost(cold)------------------------->|
 *  * .                         |<--allCost(warm)-->|
 *
 *  优化点：异步任务可以在splash页面onWindowFocusChange也就是出现窗口时候await，等任务都执行完成，才让其进入正在的main页面
 */
@com.jamesfchen.lifecycle.App
public class App extends Application implements Configuration.Provider {
//    private static AppComponent sAppComponent;
//    private static NetComponent sNetComponent;
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

//        sNetComponent = DaggerNetComponent.builder()
//                .netModule(new NetModule())
//                .build();

//        sAppComponent = DaggerAppComponent.builder()
////                .netComponent(sNetComponent)
//                .appModule(new AppModule(this))
//                .sigInModule(new SigInModule())
//                .build();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        Utils.init(this);
        Util.init(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
    }

//    public static AppComponent getAppComponent() {
//        return sAppComponent;
//    }

//    public static NetComponent getNetComponet() {
//        return sNetComponent;
//    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.VERBOSE)
                .build();
    }
}