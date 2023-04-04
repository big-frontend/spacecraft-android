package com.jamesfchen.map.service;

import android.annotation.SuppressLint;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jamesfchen.map.ILbsListener;
import com.jamesfchen.map.MapActivity;
import com.jamesfchen.map.R;
import com.jamesfchen.map.model.AppCellInfo;
import com.jamesfchen.map.model.AppLocation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

import static com.jamesfchen.map.LbsUtils.TAG_service;
import static com.jamesfchen.map.service.Constants.MIN_DISTANCE;
import static com.jamesfchen.map.service.Constants.MIN_TIMES;


public class LbsServices extends Service {
    ILbsApiServer iLbsApi;
    long count = 0;
    LocationManager locationManager;
    TelephonyManager telephonyManager;
    NotificationManager notificationManager;

    public static void startAndBindService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, LbsServices.class);
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
        Intent intent = new Intent(activity, LbsServices.class);
        activity.stopService(intent);
        activity.unbindService(connection);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        iLbsApi = new ILbsApiServer();
        return (IBinder) iLbsApi;
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
    public static final String channelId = "channelId";
    public static final String channelName = "channelName";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG_service,"onCreate");
        Context applicationContext = getApplicationContext();
        locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
        telephonyManager = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);

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
        }
        WifiReceiver.registerReceiver(this);
        realRequestLocationForAmap();
        realRequestLocation();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG_service,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WifiReceiver.unregisterReceiver(this);
        mlocationClient.stopLocation();
        Log.d(TAG_service,"onDestroy");
        Toast.makeText(this, "stop & unbind service", Toast.LENGTH_SHORT).show();
    }


    public AMapLocationClient mlocationClient = new AMapLocationClient(this);
    public AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
    @SuppressLint("MissingPermission")
    private void realRequestLocationForAmap() {
        mlocationClient.setLocationListener(new AMapLocationListener() {
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        int locationType = amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double latitude = amapLocation.getLatitude();//获取纬度
                        double longitude = amapLocation.getLongitude();//获取经度
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        String locationTypeStr;
                        switch (locationType){
//                            case 0:{locationTypeStr = "定位失败";}
                            case 1:{locationTypeStr = "GPS定位结果";}
                            case 2:{locationTypeStr = "前次定位结果";}
//                            case 3:{locationTypeStr = "缓存定位结果";}
                            case 4:{locationTypeStr = "缓存定位结果";}
                            case 5:{locationTypeStr = "Wifi定位结果";}
                            case 6:{locationTypeStr = "基站定位结果";}
//                            case 7:{locationTypeStr = "离线定位结果";}
                            case 8:{locationTypeStr = "离线定位结果";}
                            case 9:{locationTypeStr = "最后位置缓存";}
                            default:{locationTypeStr = "";}
                        }
                        Log.d(TAG_service, "IntentService onLocationChanged: locationType:"+locationTypeStr+" location" + latitude + " , " + longitude+"");
                        if (iLbsApi.listenerlist == null) return;
                        final int N = iLbsApi.listenerlist.beginBroadcast();
                        for (int i = 0; i < N; i++) {
                            ILbsListener l = iLbsApi.listenerlist.getBroadcastItem(i);
                            if (l != null) {
                                try {
                                    List<AppCellInfo> appCellInfos = new ArrayList<>();
                                    for (CellInfo cell : telephonyManager.getAllCellInfo()) {
                                        appCellInfos.add(AppCellInfo.convertSysCellInfo(cell));
                                    }
                                    count += 1;
                                    l.onLocationChanged(AppLocation.convertSysLocation(amapLocation), appCellInfos, count);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        iLbsApi.listenerlist.finishBroadcast();
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e(TAG_service, "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }

                }
            }
        });
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();

    }

    @SuppressLint("MissingPermission")
    private void realRequestLocation() {
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
                            List<AppCellInfo> appCellInfos = new ArrayList<>();
                            for (CellInfo cell : telephonyManager.getAllCellInfo()) {
                                appCellInfos.add(AppCellInfo.convertSysCellInfo(cell));
                            }
                            count += 1;
                            l.onLocationChanged(AppLocation.convertSysLocation(location), appCellInfos, count);
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
    }


}
