package com.hawksjamesf.template;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;


public class LocalService extends Service {

    public static void startAndBindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LocalService.class);
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
            Toast.makeText(activity, "start &  bind  foreground service", Toast.LENGTH_SHORT).show();
        } else {
            activity.startService(intent);
            Toast.makeText(activity, "start &  bind  service", Toast.LENGTH_SHORT).show();
        }

    }

    public static void stopAndUnbindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LocalService.class);
        activity.stopService(intent);
        activity.unbindService(connection);
    }

    @Keep
    private IMyAidlInterface mb = new IMyAidlInterface.Stub() {

        @Override
        public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            return 0;
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mb.asBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public static final int ONGOING_NOTIFICATION_ID = 100;
    public static final String channelId = "channelId_00";
    public static final String channelName = "channelName_00";

    @Override
    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            Intent notificationIntent = new Intent(this, StartActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);

            notificationManager.createNotificationChannel(chan);
            Notification notification = new Notification.Builder(this, channelId)
                    .setContentTitle("this is title")
                    .setContentText("this is text")
                    .setContentIntent(pendingIntent)
                    .setTicker("this is ticker")
                    .build();
            startForeground(ONGOING_NOTIFICATION_ID, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
