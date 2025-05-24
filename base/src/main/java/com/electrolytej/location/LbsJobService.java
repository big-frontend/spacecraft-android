package com.electrolytej.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;


public class LbsJobService extends JobService {

    private static JobScheduler mJobScheduler;
    LocationManager locationManager;
    TelephonyManager telephonyManager;
    public static void startService(Activity activity) {
        mJobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo = new JobInfo.Builder(0, new ComponentName(activity, LbsJobService.class))
                .setPersisted(true) //设备重启后是否继续执行
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)// 需要满足网络条件，默认值NETWORK_TYPE_NONE
//                .setMinimumLatency(5000)// 任务最少延迟时间为5s
//                .setOverrideDeadline(60000)// 任务deadline，当到期没达到指定条件也会开始执行
                .setPeriodic(AlarmManager.INTERVAL_FIFTEEN_MINUTES) //循环执行，循环时长为一天（最小为15分钟）
//                .setRequiresCharging(true)// 需要满足充电状态
//                .setRequiresDeviceIdle(false)// 设备处于Idle(Doze)
//                .setBackoffCriteria(3000,JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略
                .build();
        mJobScheduler.schedule(jobInfo);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
        Log.d(Constants.TAG, "onStartJob: Is it main thread ? " + b);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MIN_TIMES, Constants.MIN_DISTANCE, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
                Log.d(Constants.TAG, "JobService  onLocationChanged: Is it main thread ? " + b);
                Log.d(Constants.TAG, "JobService  onLocationChanged: location" + location.getLatitude() +" , "+location.getLongitude());
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
        },Looper.getMainLooper());

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTimes,minDistance,createPendingIntent());
//        locationManager.requestLocationUpdates(minTimes,minDistance,createCriteria(),createPendingIntent());
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
                Log.d(Constants.TAG, "onStartJob sindle onLocationChanged: Is it main thread ? " + b);
                Log.d(Constants.TAG, "onStartJob sindle onLocationChanged: location" + location.getLatitude() +" , "+location.getLongitude());

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
        },Looper.getMainLooper());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        boolean b = Looper.getMainLooper().getThread() == Thread.currentThread();
        Log.d(Constants.TAG, "onStopJob: Is it main thread ? " + b);
        return true;
    }


}
