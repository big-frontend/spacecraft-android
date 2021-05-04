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
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: May/04/2021  Tue
 */
public class MessengerService extends Service {
    public static void startAndBindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MessengerService.class);
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
        Intent intent = new Intent(activity, MessengerService.class);
        activity.stopService(intent);
        activity.unbindService(connection);
    }

    private Messenger serverMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {

                Log.d("cjf", "print " + msg.getData().getString("cjf"));
                Message replyMsg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("cjf", "server");
                replyMsg.setData(b);
                msg.replyTo.send(replyMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serverMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public static final int ONGOING_NOTIFICATION_ID = 102;
    public static final String channelId = "channelId_02";
    public static final String channelName = "channelName_02";

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
