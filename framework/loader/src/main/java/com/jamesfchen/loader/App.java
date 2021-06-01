package com.jamesfchen.loader;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.jamesfchen.common.DynamicConfigImplDemo;
import com.jamesfchen.common.MessageStatic;
import com.jamesfchen.common.TestPluginListener;
import com.jamesfchen.common.util.Util;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
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
import androidx.multidex.MultiDexApplication;
import androidx.work.Configuration;


/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */

public class App extends MultiDexApplication implements Configuration.Provider {
    protected static final String TAG = "SpacecraftApp---";
//    private static AppComponent sAppComponent;
//    private static NetComponent sNetComponent;
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
        MessageStatic.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
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
//        sNetComponent = DaggerNetComponent.builder()
//                .netModule(new NetModule())
//                .build();

//        sAppComponent = DaggerAppComponent.builder()
////                .netComponent(sNetComponent)
//                .appModule(new AppModule(this))
//                .sigInModule(new SigInModule())
//                .build();

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
//        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
//                .setApplicationId(BuildConfig.APPLICATION_ID)
//                .setApiKey("AIzaSyC17Cg6xF-jk_ABR3_6OtYD3VBWFeoXKWY")
//                .setProjectId("spacecraft-22dc1")
//                .setStorageBucket("spacecraft-22dc1.appspot.com")
//                .build();
//        FirebaseApp.initializeApp(this,firebaseOptions);
//        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
//        crashlytics.setCrashlyticsCollectionEnabled(true);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());



//        sFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .setFetchTimeoutInSeconds(60 * 60)
//                .build();
//        sFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        sFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
//        sFirebaseRemoteConfig.ensureInitialized();
//        sFirebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        Log.i(TAG, "onComplete-perf_enable:" + sFirebaseRemoteConfig.getBoolean("perf_enable"));
//                        FirebasePerformance.getInstance().setPerformanceCollectionEnabled(sFirebaseRemoteConfig.getBoolean("perf_enable"));
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i(TAG, "onFailure");
//                    }
//                })
//                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean aBoolean) {
//                        Log.i(TAG, "onSuccess:" + aBoolean);
//                    }
//                })
//                .addOnCanceledListener(new OnCanceledListener() {
//                    @Override
//                    public void onCanceled() {
//                        Log.i(TAG, "onCanceled");
//                    }
//                });

        startMatrix();

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

//        Fresco.initialize(this);
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);


    }
    private void startMatrix(){
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        MatrixLog.i(TAG, "MatrixApplication.onCreate");

        Matrix.Builder builder = new Matrix.Builder(this);
        builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("com.hawksjamesf.myhome.ui.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);

        if (matrixEnable) {

            //resource
            builder.plugin(new ResourcePlugin(new ResourceConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .setDumpHprof(false)
                    .setDetectDebuger(true)     //only set true when in sample, not in your app
                    .build()));
            ResourcePlugin.activityLeakFixer(this);

            //io
            IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .build());
            builder.plugin(ioCanaryPlugin);


            // prevent api 19 UnsatisfiedLinkError
            //sqlite
            SQLiteLintConfig config = initSQLiteLintConfig();
            SQLiteLintPlugin sqLiteLintPlugin = new SQLiteLintPlugin(config);
            builder.plugin(sqLiteLintPlugin);

//            ThreadWatcher threadWatcher = new ThreadWatcher(new ThreadConfig.Builder().dynamicConfig(dynamicConfig).build());
//            builder.plugin(threadWatcher);


        }

        Matrix.init(builder.build());

        //start only startup tracer, close other tracer.
        tracePlugin.start();
//        Matrix.with().getPluginByClass(ThreadWatcher.class).start();
        MatrixLog.i("Matrix.HackCallback", "end:%s", System.currentTimeMillis());
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


//    public static AppComponent getAppComponent() {
//        return sAppComponent;
//    }

//    public static NetComponent getNetComponet() {
//        return sNetComponent;
//    }

    public static FirebaseRemoteConfig getFirebaseRemoteConfig() {
        return sFirebaseRemoteConfig;
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.VERBOSE)
                .build();
    }
}