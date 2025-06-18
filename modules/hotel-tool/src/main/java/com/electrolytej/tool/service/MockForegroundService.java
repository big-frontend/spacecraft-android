package com.electrolytej.tool.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.electrolytej.mockserver.IMockApi;
import com.electrolytej.mockserver.IMockServerCallback;
import com.electrolytej.tool.Constants;

public class MockForegroundService extends Service {
    private static final String TAG = Constants.TAG + "/MockFGService";

    private IMockApi api = new IMockApi.Stub() {


        @Override
        public void register(IMockServerCallback callback) throws RemoteException {

        }
    };

    public static void bindAndStartService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MockForegroundService.class);
        intent.putExtra("cjf", "cjf123412");
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
        } else {
            activity.startService(intent);
        }

    }


    public static void unbindAndStopService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MockForegroundService.class);
        activity.unbindService(connection);
        activity.stopService(intent);

    }

    public static final int ONGOING_NOTIFICATION_ID = 100;
    public static final String channelId = "channelId_00";
    public static final String channelName = "channelName_00";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//            Intent notificationIntent = new Intent(this, LogActivity.class);
//            PendingIntent pendingIntent =
//                    PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//            notificationManager.createNotificationChannel(chan);
//            Notification notification = new Notification.Builder(this, channelId)
//                    .setContentTitle("this is title")
//                    .setContentText("this is text")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    .setTicker("this is ticker")
//                    .build();
//            startForeground(ONGOING_NOTIFICATION_ID, notification);
        }
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
     * - 当service采用绑定启动，那么被kill掉之后，service进程会重启，会执行onCreate，onBind，不会执行onStartCommand
     * - 当service没有采用绑定启动，那么被kill之后不会再重启
     * START_REDELIVER_INTENT：被kill掉之后，service进程会重启，会执行onCreate，onBind，但是不会执行onStartCommand. Intent将重新传
     */
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        String a = "-1";
        if (intent != null) {
            a = intent.getStringExtra("cjf");
        }
        Log.d(TAG, "startId:" + startId + " intent value:" + a);
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
