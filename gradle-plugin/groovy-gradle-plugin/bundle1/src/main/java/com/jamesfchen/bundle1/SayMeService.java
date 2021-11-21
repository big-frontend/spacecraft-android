package com.jamesfchen.bundle1;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 */
public class SayMeService extends LifecycleService {
    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getLifecycle().addObserver(new ServiceLifecycleObserver());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
