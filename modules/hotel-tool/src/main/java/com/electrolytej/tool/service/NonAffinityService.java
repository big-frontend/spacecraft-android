package com.electrolytej.tool.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.electrolytej.mockserver.IMockApi;
import com.electrolytej.mockserver.IMockServerCallback;
import com.electrolytej.tool.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NonAffinityService extends Service {
    private static final String TAG = Constants.TAG + "/NonAffinityService";

    private IMockApi api = new IMockApi.Stub() {

        @Override
        public void register(IMockServerCallback callback) throws RemoteException {

        }
    };

    public static void bindAndStartService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, NonAffinityService.class);
//        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        activity.startService(intent);

    }

    public static void unbindAndStopService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, NonAffinityService.class);
        activity.unbindService(connection);
        activity.stopService(intent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        MockService.bindAndStartService(this, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return api.asBinder();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
