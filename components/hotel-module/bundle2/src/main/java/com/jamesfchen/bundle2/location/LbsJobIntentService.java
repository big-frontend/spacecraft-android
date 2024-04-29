package com.jamesfchen.bundle2.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import static com.jamesfchen.bundle2.util.LbsUtils.TAG_service;

public class LbsJobIntentService extends JobIntentService {
    private static final int JOB_ID = 2;
    LocationManager locationManager;
    TelephonyManager telephonyManager;

    public static void startService(Activity activity) {
        Intent intent = new Intent(activity, LbsJobIntentService.class);
        enqueueWork(activity, LbsJobIntentService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
        Log.d(TAG_service, "onHandleWork: Is it main thread ? " + b);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MIN_TIMES, Constants.MIN_DISTANCE, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
                Log.d(TAG_service, "JobIntentService onLocationChanged: Is it main thread ? " + b);
                Log.d(TAG_service, "JobIntentService onLocationChanged: location" + location.getLatitude() + " , " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }, Looper.getMainLooper());

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTimes,minDistance,createPendingIntent());
//        locationManager.requestLocationUpdates(minTimes,minDistance,createCriteria(),createPendingIntent());
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
                Log.d(TAG_service, "sindle onLocationChanged: Is it main thread ? " + b);
                Log.d(TAG_service, "sindle onLocationChanged: location" + location.getLatitude() + " , " + location.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }, Looper.getMainLooper());

    }

    private PendingIntent createPendingIntent() {
        return null;
    }

    private Criteria createCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }
}
