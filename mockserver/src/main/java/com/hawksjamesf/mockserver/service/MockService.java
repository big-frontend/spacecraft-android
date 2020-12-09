package com.hawksjamesf.mockserver.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hawksjamesf.mockserver.Constants;
import com.hawksjamesf.mockserver.IMockApi;
import com.hawksjamesf.mockserver.IMockServerCallback;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class MockService extends Service {
    private static final String TAG = Constants.TAG + "/MockService";

    private IMockApi api = new IMockApi.Stub() {

        @Override
        public void register(IMockServerCallback callback) throws RemoteException {

        }
    };

    public static void bindAndStartService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MockService.class);
        intent.putExtra("cjf", "cjf123412");
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        activity.startService(intent);

    }

    public static void unbindAndStopService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MockService.class);
        activity.unbindService(connection);
        activity.stopService(intent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return api.asBinder();
    }

    /**
     * 当MockService进程被kill掉，经过短暂的几秒系统会自动重启进程
     * START_STICKY：被kill掉之后，service进程会重启，会执行onCreate，onBind，onStartComman. Intent将为null
     * START_NOT_STICKY:
     *  - 当service采用绑定启动，那么被kill掉之后，service进程会重启，会执行onCreate，onBind，不会执行onStartCommand
     *  - 当service没有采用绑定启动，那么被kill之后不会再重启
     * START_REDELIVER_INTENT：被kill掉之后，service进程会重启，会执行onCreate，onBind，但是不会执行onStartCommand. Intent将重新传
     */
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        String a = "-1";
        if (intent != null) {
            a = intent.getStringExtra("cjf");
        }
        Log.d(TAG, "startId:" + startId + " value:" + a);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
