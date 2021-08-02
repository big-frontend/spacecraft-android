package com.jamesfchen.loader;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.jamesfchen.common.MessageStatic;
import com.orhanobut.logger.Logger;

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
import com.jamesfchen.common.util.Util;
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

        String processName = getProcessName(android.os.Process.myPid());
        Logger.t(TAG).d("processName：" + processName);
//        if (TextUtils.isEmpty(processName)|| processName.contains(PROCESS_2)||processName.contains(PROCESS_3)) return;
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