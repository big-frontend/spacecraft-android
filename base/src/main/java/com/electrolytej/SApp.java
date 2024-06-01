package com.electrolytej;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.work.Configuration;

import com.alibaba.android.alpha.OnProjectExecuteListener;
import com.electrolytej.loader.alialpha.ConfigTest;
import com.electrolytej.util.Util;
import com.google.firebase.perf.metrics.AddTrace;


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: 2017/7/4
 *
 */
public class SApp extends Application implements Configuration.Provider {
    private static SApp app;
    public static SApp getInstance() {
        if (app == null) {
            throw new IllegalStateException("app is null");
        }
        return app;
    }

    private static final String PROCESS_1 = "com.hawksjamesf.spacecraft.debug";
    private static final String PROCESS_2 = ":mock_server";
    private static final String PROCESS_3 = ":mock_jobserver";
    private long start = 0;
//    public SApp() {
//        super(
//                //tinkerFlags, which types is supported
//                //dex only, library only, all support
//                ShareConstants.TINKER_ENABLE_ALL,
//                // This is passed as a string so the shell application does not
//                // have a binary dependency on your ApplicationLifeCycle class.
//                "com.jamesfchen.loader.SAppLike");
//    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        app = this;
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
//        Debug.stopMethodTracing();
        Trace.endSection();
        super.onCreate();
//        SoLoader.init(this, /* native exopackage */ false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        Utils.init(this);
        Util.init(this);
//        SystemFilter.init(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
        ConfigTest test = new ConfigTest(getApplicationContext());
        test.setOnProjectExecuteListener(new OnProjectExecuteListener() {
            @Override
            public void onProjectStart() {

            }

            @Override
            public void onTaskFinish(String taskName) {

            }

            @Override
            public void onProjectFinish() {
            }
        });
        test.start();
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
//
//    @Override
//    public ReactNativeHost getReactNativeHost() {
//        return new ReactNativeHost(this) {
//            @Override
//            public boolean getUseDeveloperSupport() {
//                return BuildConfig.DEBUG;
//            }
//
//            @Override
//            protected List<ReactPackage> getPackages() {
//                return Arrays.<ReactPackage>asList(new ReactPackage() {
//                    @Override
//                    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
//                        return Collections.emptyList();
//                    }
//
//                    @Override
//                    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
//                        return Collections.singletonList(new MyNativeViewManager());
//                    }
//                });
//            }
//        };
//    }
}