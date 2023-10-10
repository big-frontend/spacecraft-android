package com.jamesfchen.vi;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.P)//android 9
public class MyCoreComponentFactory extends AppComponentFactory {
    public static final String TAG = "MyCoreComponentFactory";
    @NonNull
    @Override
    public ClassLoader instantiateClassLoader(@NonNull ClassLoader cl, @NonNull ApplicationInfo aInfo) {
        return super.instantiateClassLoader(cl, aInfo);
    }

    @NonNull
    @Override
    public Application instantiateApplication(@NonNull ClassLoader cl, @NonNull String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.instantiateApplication(cl, className);
    }

    @NonNull
    @Override
    public Activity instantiateActivity(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        return super.instantiateActivity(cl, className, intent);
    }

    @NonNull
    @Override
    public BroadcastReceiver instantiateReceiver(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.instantiateReceiver(cl, className, intent);
    }

    @NonNull
    @Override
    public Service instantiateService(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.instantiateService(cl, className, intent);
    }

    @NonNull
    @Override
    public ContentProvider instantiateProvider(@NonNull ClassLoader cl, @NonNull String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Log.e(TAG_STARTUP_MONITOR, "MyCoreComponentFactory instantiateProvider "+className);
        return super.instantiateProvider(cl, className);
    }
}
