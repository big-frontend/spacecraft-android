package com.hawksjamesf.map.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hawksjamesf.map.dao.LbsDb;
import com.hawksjamesf.map.model.AppCellInfo;
import com.hawksjamesf.map.model.AppLocation;
import com.hawksjamesf.map.model.LBS;


public class LbsIntentServices extends IntentService {
    public static final int minTimes = 10;
    public static final int minDistance = 100;
    ILbsApiStub iLbsApi = new ILbsApiStub();
    long count = 0;
    LocationManager locationManager;
    TelephonyManager telephonyManager;

    public static void startAndBindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LbsIntentServices.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            activity.startForegroundService(intent);
        } else {
        }
        activity.startService(intent);
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Toast.makeText(activity, "start &  bind  service", Toast.LENGTH_LONG).show();

    }

    public static void stopAndUnbindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LbsIntentServices.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            activity.startForegroundService(intent);
        } else {
        }
        activity.stopService(intent);
        activity.unbindService(connection);
        Toast.makeText(activity, "stop & unbind Service", Toast.LENGTH_LONG).show();

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

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimes, minDistance, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                if (iLbsApi.listenerlist == null) return;
//                final int N = iLbsApi.listenerlist.beginBroadcast();
//                for (int i = 0; i < N; i++) {
//                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
//                    if (l != null) {
//                        try {
//                            List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
//                            count += 1;
//                            l.onLocationChanged(location, allCellInfo, count);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                iLbsApi.listenerlist.finishBroadcast();
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//                if (iLbsApi.listenerlist == null) return;
//                final int N = iLbsApi.listenerlist.beginBroadcast();
//                for (int index = 0; index < N; i++) {
//                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(index);
//                    if (l != null) {
//                        try {
//                            l.onStatusChanged(s, i, bundle);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                iLbsApi.listenerlist.finishBroadcast();
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//                if (iLbsApi.listenerlist == null) return;
//                final int N = iLbsApi.listenerlist.beginBroadcast();
//                for (int i = 0; i < N; i++) {
//                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
//                    if (l != null) {
//                        try {
//                            l.onProviderEnabled(s);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                iLbsApi.listenerlist.finishBroadcast();
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//                if (iLbsApi.listenerlist == null) return;
//                final int N = iLbsApi.listenerlist.beginBroadcast();
//                for (int i = 0; i < N; i++) {
//                    ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
//                    if (l != null) {
//                        try {
//                            l.onProviderDisabled(s);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                iLbsApi.listenerlist.finishBroadcast();
//
//            }
//        }, Looper.getMainLooper());

        while (true) {
            try {
                Thread.sleep(500);
                AppCellInfo appCellInfo = new AppCellInfo();
                appCellInfo.cid = 12321231;
                appCellInfo.lac = 12321;
                AppLocation appLocation = new AppLocation();
                appLocation.lat = 12312321.123;
                appLocation.lon = 78437790.237;
                LBS lbs = new LBS(0,appCellInfo,appLocation);
                LbsDb.Companion.get(this).lbsDao().insert(lbs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("LbsIntentServices", "onHandleIntent:" + count);
        }
    }

}
