package com.hawksjamesf.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
public class AudioService extends Service {
    public static final String TAG = AudioService.class.getSimpleName();

    private AudioServiceApiImpl mAudioServiceApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioServiceApi = new AudioServiceApiImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // bindService/unbindService
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAudioServiceApi;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
    // bindService/unbindService

    // startService/stopService
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    // startService/stopService


}
