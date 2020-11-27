package com.dianping.v1;

import android.app.ActivityThread;
import android.content.pm.IPackageManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/27/2020  Fri
 */
public class Hook {
    public static void init(ClassLoader classLoader){
        IPackageManager origin = ActivityThread.getPackageManager();
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        IPackageManager packageManagerProxy = (IPackageManager) Proxy.newProxyInstance(classLoader, new Class[]{IPackageManager.class}, new PackageManagerProxy(origin));
        try {
            Field sPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            sPackageManagerField.set(activityThread,packageManagerProxy);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.d("hook",Log.getStackTraceString(e));
            e.printStackTrace();
        }


    }
}
