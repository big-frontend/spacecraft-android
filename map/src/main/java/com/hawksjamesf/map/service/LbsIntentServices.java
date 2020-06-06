package com.hawksjamesf.map.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.hawksjamesf.map.ILbsListener;
import com.hawksjamesf.map.MapActivity;
import com.hawksjamesf.map.R;
import com.hawksjamesf.map.model.AppCellInfo;
import com.hawksjamesf.map.model.AppLocation;

import java.util.List;

import androidx.annotation.Nullable;

import static com.hawksjamesf.map.LbsUtils.TAG_service;
import static com.hawksjamesf.map.service.Constants.MIN_DISTANCE;
import static com.hawksjamesf.map.service.Constants.MIN_TIMES;


public class LbsIntentServices extends IntentService {
    ILbsApiStub iLbsApi = new ILbsApiStub();
    long count = 0;
    LocationManager locationManager;
    TelephonyManager telephonyManager;
    NotificationManager notificationManager;

    public static void startAndBindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LbsIntentServices.class);
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
        Toast.makeText(activity, "start &  bind  foreground service", Toast.LENGTH_LONG).show();
        } else {
            activity.startService(intent);
        Toast.makeText(activity, "start &  bind  service", Toast.LENGTH_LONG).show();
        }

    }

    public static void stopAndUnbindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LbsIntentServices.class);
        activity.stopService(intent);
        activity.unbindService(connection);
        Toast.makeText(activity, "stop & unbind service", Toast.LENGTH_LONG).show();

    }

    public LbsIntentServices() {
        super("LbsServices");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) iLbsApi;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public static final int ONGOING_NOTIFICATION_ID = 100;
    public static final String channelId = "channelId";
    public static final String channelName = "channelName";

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            Intent notificationIntent = new Intent(this, MapActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);

            notificationManager.createNotificationChannel(chan);
            Notification notification = new Notification.Builder(this, channelId)
                    .setContentTitle("this is title")
                    .setContentText("this is text")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setTicker("this is ticker")
                    .build();
            startForeground(ONGOING_NOTIFICATION_ID, notification);
//            stopForeground(ONGOING_NOTIFICATION_ID);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        List<String> enableProviders = locationManager.getProviders(true);
        List<String> allProviders = locationManager.getAllProviders();
        Log.d("LBS_collection", "onHandleIntent: enable provider:" + enableProviders.toString());
        Log.d("LBS_collection", "onHandleIntent: all provider:" + allProviders);
        String choice;
        boolean gpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gpsProviderEnabled) {
            choice = LocationManager.GPS_PROVIDER;
        } else {
            boolean netProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (netProviderEnabled) {
                choice = LocationManager.NETWORK_PROVIDER;
            } else {
                choice = LocationManager.PASSIVE_PROVIDER;
            }
        }
        Log.d("LBS_collection", "onHandleIntent: choice provider:" + choice);
        locationManager.requestLocationUpdates(choice, MIN_TIMES, MIN_DISTANCE, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG_service, "IntentService onLocationChanged: location" + location.getLatitude() + " , " + location.getLongitude());
                if (iLbsApi.listenerlist == null) return;
                final int N = iLbsApi.listenerlist.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
                    if (l != null) {
                        try {
                            AppCellInfo appCellInfo = null;
                            List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
                            if (!allCellInfo.isEmpty()) {
                                appCellInfo = AppCellInfo.convertSysCellInfo(allCellInfo.get(0));
                            }
                            count += 1;
                            l.onLocationChanged(AppLocation.convertSysLocation(location), appCellInfo, count);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                iLbsApi.listenerlist.finishBroadcast();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                if (iLbsApi.listenerlist == null) return;
                final int N = iLbsApi.listenerlist.beginBroadcast();
                for (int index = 0; index < N; i++) {
                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(index);
                    if (l != null) {
                        try {
                            l.onStatusChanged(s, i, bundle);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                iLbsApi.listenerlist.finishBroadcast();
            }

            @Override
            public void onProviderEnabled(String s) {
                if (iLbsApi.listenerlist == null) return;
                final int N = iLbsApi.listenerlist.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
                    if (l != null) {
                        try {
                            l.onProviderEnabled(s);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                iLbsApi.listenerlist.finishBroadcast();
            }

            @Override
            public void onProviderDisabled(String s) {
                if (iLbsApi.listenerlist == null) return;
                final int N = iLbsApi.listenerlist.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
                    if (l != null) {
                        try {
                            l.onProviderDisabled(s);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                iLbsApi.listenerlist.finishBroadcast();

            }
        }, Looper.getMainLooper());
//        while (true) {
//            try {
//                Thread.sleep(500);
//                AppCellInfo appCellInfo = new AppCellInfo();
//                appCellInfo.cid = 12321231;
//                appCellInfo.lac = 12321;
//                AppLocation appLocation = new AppLocation();
//                appLocation.lat = 12312321.123;
//                appLocation.lon = 78437790.237;
//                LBS lbs = new LBS();
//                lbs.setAppCellInfo(appCellInfo);
//                lbs.setAppLocation(appLocation);
//                LbsDb.Companion.get(this).lbsDao().insert(lbs);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d("LbsIntentServices", "onHandleIntent:" + count);
//        }
    }

}
