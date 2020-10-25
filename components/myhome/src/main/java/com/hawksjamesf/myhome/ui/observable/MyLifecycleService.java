package com.hawksjamesf.myhome.ui.observable;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/16/2019  Sat
 */
public class MyLifecycleService extends LifecycleService {
    @Override
    public void onCreate() {
        super.onCreate();
        getLifecycle().addObserver(new ServiceLifecycleObserver());
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onStart(@NonNull Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
