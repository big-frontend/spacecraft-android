package com.hawksjamesf.common.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.blankj.utilcode.util.PermissionUtils;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Oct/31/2018  Wed
 */
public final class Util {
    private static Application sApp;
    static List<Activity> sActivityList = new LinkedList<>();//比起ArrayList插入/删除的速度更快
    /*
     *SoftReference 类的一个典型用途就是用于内存敏感的高速缓存。 SoftReference 的原理是：在保持对对象的引用时保证在 JVM 报告内存不足情况之前将清除所有的软引用。关键之处在于，垃圾收集器在运行时可能会（也可能不会）释放软可及对象。对象是否被释放取决于垃圾收集器的算法以及垃圾收集器运行时可用的内存数量
     * WeakReference 类的一个典型用途就是规范化映射（canonicalized mapping)。
     * 另外，对于那些生存期相对较长而且重新创建的开销也不高的对象来说，WeakReference也比较有用
     */
    static WeakReference<Activity> sTopActivityWeakRef;


    private static Application.ActivityLifecycleCallbacks mCallback = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Logger.t("Util-onActivityCreated").d(activity);
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Logger.t("Util-onActivityDestroyed").d(activity);
            sActivityList.remove(activity);

        }
    };

    private static void setTopActivityWeakRef(Activity activity) {
        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
        //不能使用强引用，当栈顶的Activity被销毁时，由于Util类的静态字段持有Activity对象导致其占有的内存不能被释放，造成内存泄漏。
        //故当静态字段引用周期类时，需要使用WeakReference
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            Logger.t("Util-setTopActivityWeakRef").d(activity);
            sTopActivityWeakRef = new WeakReference<>(activity);
        }

    }

    public static Application getApp() {
        if (sApp != null) {
            return sApp;
        } else {
            throw new NullPointerException("sApp is null");
        }
    }

    public static void init(@NonNull Application app) {
        sApp = app;
        app.registerActivityLifecycleCallbacks(mCallback);
    }

}
