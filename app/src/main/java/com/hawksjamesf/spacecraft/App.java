package com.hawksjamesf.spacecraft;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hawksjamesf.common.DynamicConfigImplDemo;
import com.hawksjamesf.common.TestPluginListener;
import com.hawksjamesf.common.util.Util;
import com.hawksjamesf.network.DaggerNetComponent;
import com.hawksjamesf.network.NetComponent;
import com.hawksjamesf.network.NetModule;
import com.hawksjamesf.spacecraft.ui.observable.AppLifecycleObserver;
import com.hawksjamesf.spacecraft.ui.signin.SigInModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.sqlitelint.SQLiteLint;
import com.tencent.sqlitelint.SQLiteLintPlugin;
import com.tencent.sqlitelint.config.SQLiteLintConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.provider.FontRequest;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
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

    private static final String PROCESS_1 = "com.hawksjamesf.spacecraft.debug";
    private static final String PROCESS_2 = ":mock_server";
    private static final String PROCESS_3 = ":mock_jobserver";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(TAG).build()) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        String processName = getProcessName(android.os.Process.myPid());
        Logger.t(TAG).d("processName：" + processName);
        if (TextUtils.isEmpty(processName)|| processName.contains(PROCESS_2)||processName.contains(PROCESS_3)) return;
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
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        strategy.setAppReportDelay(20000);   //改为20s
        strategy.setAppChannel("huawei");
//        CrashReport.setUserSceneTag(context, 9527); // 上报后的Crash会显示该标签
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), strategy);
        Fabric.with(this, new Crashlytics());
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


        MatrixLog.i(TAG, "MatrixApplication.onCreate");
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();
        //io
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
//                .dynamicConfig(dynamicConfig)
                .build());
        //trace
        TracePlugin tracePlugin = (new TracePlugin(new TraceConfig.Builder()
//                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("sample.tencent.matrix.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build()));
        //resource
        ResourcePlugin resourcePlugin = new ResourcePlugin(new ResourceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .setDumpHprof(false)
                .setDetectDebuger(true)     //only set true when in sample, not in your app
                .build());
        ResourcePlugin.activityLeakFixer(this);
        // prevent api 19 UnsatisfiedLinkError
        //sqlite
        SQLiteLintConfig config = initSQLiteLintConfig();
        SQLiteLintPlugin sqLiteLintPlugin = new SQLiteLintPlugin(config);

//        ThreadWatcher threadWatcher = new ThreadWatcher(new ThreadConfig.Builder().dynamicConfig(dynamicConfig).build());
        Matrix matrix = new Matrix.Builder(this)
                .patchListener(new TestPluginListener(this)) // add general pluginListener
                .plugin(ioCanaryPlugin)
                .plugin(tracePlugin)
                .plugin(resourcePlugin)
//                .plugin(threadWatcher)
                .build();
//        Matrix.init(matrix);
//        ioCanaryPlugin.start();
//        Matrix.with().getPluginByClass(ThreadWatcher.class).start();
        MatrixLog.i("Matrix.HackCallback", "end:%s", System.currentTimeMillis());

        FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "emoji compat Font Query",
                R.array.com_google_android_gms_fonts_certs);
        EmojiCompat.Config emojiCompatConfig = new FontRequestEmojiCompatConfig(this, fontRequest)
                .setReplaceAll(true)
                .setEmojiSpanIndicatorColor(Color.GREEN)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        super.onInitialized();
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                        super.onFailed(throwable);
                    }
                })
                .setEmojiSpanIndicatorEnabled(true);
        EmojiCompat.Config bundledEmojiCompatConfig = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(emojiCompatConfig);

        Fresco.initialize(this);

    }

    private static SQLiteLintConfig initSQLiteLintConfig() {
        try {
            /**
             * HOOK模式下，SQLiteLint会自己去获取所有已执行的sql语句及其耗时(by hooking sqlite3_profile)
             * @see 而另一个模式：SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY , 则需要调用 {@link SQLiteLint#notifySqlExecution(String, String, int)}来通知
             * SQLiteLint 需要分析的、已执行的sql语句及其耗时
             * @see TestSQLiteLintActivity#doTest()
             */
            return new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK);
        } catch (Throwable t) {
            return new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK);
        }
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