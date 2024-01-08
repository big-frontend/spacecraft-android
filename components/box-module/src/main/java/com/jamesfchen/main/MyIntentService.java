package com.jamesfchen.main;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.jamesfchen.mockserver.service.MockIntentService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("test intent");
    }
    public static void bindAndStartService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MyIntentService.class);
//        activity.bindService(intent, connection, 129);
        activity.startService(intent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("MyIntentService","onStart");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("MyIntentService","onHandleIntent");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService","onDestroy");
    }
}
