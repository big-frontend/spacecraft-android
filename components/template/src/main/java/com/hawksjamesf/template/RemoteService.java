package com.jamesfchen.template;

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
import android.widget.Toast;

import com.jamesfchen.template.ipc.BinderEntry;

import androidx.annotation.Nullable;


public class RemoteService extends Service {

    public static void startAndBindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, RemoteService.class);
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
        Intent intent = new Intent(activity, RemoteService.class);
        activity.stopService(intent);
        activity.unbindService(connection);
    }

    private BinderEntry mBinderEntry = new BinderEntry();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderEntry;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public static final int ONGOING_NOTIFICATION_ID = 101;
    public static final String channelId = "channelId_01";
    public static final String channelName = "channelName_01";

    @Override
    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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
