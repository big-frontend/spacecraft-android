package com.dianping.v1;

import android.app.ActivityThread;
import android.content.pm.IPackageManager;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/27/2020  Fri
 */
public class Hook {
    private static void printAttrib(Class<?> clz) {
        Log.d("cjf", "clz:" + clz.getName());
        Class<?>[] declaredClasses = clz.getDeclaredClasses();
        for (Class<?> c : declaredClasses) {
            Log.d("cjf", "inner clz:" + c.getName());
        }
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            Log.d("cjf", "field name：" + f.getName() + " ");

        }
        Method[] declaredMethods = clz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            m.setAccessible(true);
            Log.d("cjf", "method name：" + m.getName() + " ");

        }
    }

    public static void init(ClassLoader classLoader) {
        IPackageManager pkgMgrOrigin = ActivityThread.getPackageManager();
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        IPackageManager packageManagerProxy = (IPackageManager) Proxy.newProxyInstance(classLoader, new Class[]{IPackageManager.class}, new PackageManagerProxy(pkgMgrOrigin));
        try {
            Field sPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            sPackageManagerField.set(activityThread, packageManagerProxy);

            IBinder iBinderOrigin = ServiceManager.getService("package");

            IBinder iBinderProxy = (IBinder) Proxy.newProxyInstance(classLoader, new Class[]{IBinder.class}, new IBinderProxy(iBinderOrigin, packageManagerProxy, classLoader));
            Field sCacheField = ServiceManager.class.getDeclaredField("sCache");
            sCacheField.setAccessible(true);
            Map<String, IBinder> sCache = (Map<String, IBinder>) sCacheField.get(null);
            sCache.remove("package");
            sCache.put("package", iBinderProxy);
        } catch (Exception e) {
            Log.d("cjf", Log.getStackTraceString(e));
            e.printStackTrace();
        }


    }
}
