package com.hawksjamesf.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Oct/24/2018  Wed
 */
public class ActivityUtil {
    public static boolean isActivityExists(@NonNull final String packageName, @NonNull final String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        ResolveInfo resolveInfo = Util.getApp().getPackageManager().resolveActivity(intent, 0);
        ComponentName componentName = intent.resolveActivity(Util.getApp().getPackageManager());
        //PackageManager.PERMISSION_GRANTED=0
        List<ResolveInfo> resolveInfos = Util.getApp().getPackageManager().queryIntentActivities(intent, 0);
        return !(resolveInfo == null || componentName == null || resolveInfos.size() == 0);
    }

    public static boolean isActivityExistsInStack(@NonNull final Activity activity) {
        List<Activity> activityList = Util.sActivityList;
//        return sActivityList.contains(activity);
        for (Activity theActivity : activityList) {
            if (theActivity.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActivityExistsInStack(@NonNull final Class<?> clazz) {
        List<Activity> activityList = Util.sActivityList;
        for (Activity activity :
                activityList) {
            if (activity.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static Activity getTopActivity() {
        if (Util.sTopActivityWeakRef != null) {
            Activity activity = Util.sTopActivityWeakRef.get();
            if (activity != null) {
                return activity;
            }
        }
        List<Activity> activityList = Util.sActivityList;
        int size = activityList.size();
        return size > 0 ? activityList.get(size - 1) : null;

    }

    public static Context getActivityOrApp() {
        Activity topActivity = getTopActivity();
        return topActivity == null ? Util.getApp() : topActivity;
    }

    private static void startActivity(@NonNull final Context context,
                                      final Bundle extras,
                                      @NonNull final String pkg,
                                      @NonNull final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);

    }

    private static void startActivity(@NonNull final Intent intent,
                                      @NonNull final Context context,
                                      final Bundle options) {
        if (!(context instanceof Activity)) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * 系统提供启动startActivity有两种，
     * - public void startActivity (Intent intent)
     * - public void startActivity (Intent intent，Bundle options) options这个参数用来表示Activity的转场效果
     *
     * @param activity
     * @param clazz
     * @param finish
     */
    public static void startActivity(Activity activity, Class clazz, boolean finish) {
        activity.startActivity(new Intent(activity, clazz));
        if (finish) {
            activity.finish();
        }
    }

    public static void startACtivity(@NonNull final Class<?> clazz) {
        Context context = getActivityOrApp();
        startActivity(context, null, context.getPackageName(), clazz.getName(), null);
    }

    public static void startACtivity(@NonNull final Class<?> clazz, @NonNull final Bundle options) {
        Context context = getActivityOrApp();
        startActivity(context, null, context.getPackageName(), clazz.getName(), options);

    }

    public static void startACtivity(@NonNull final Class<?> clazz, @NonNull final int enterAnim, @NonNull final int exitAnim) {
        Context context = getActivityOrApp();
        startActivity(context, null, context.getPackageName(), clazz.getName(), getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity)
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
    }

    //===============================================================================
    public static void startActivity(@NonNull final Activity activity, @NonNull final Class<?> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    public static void startActivity(@NonNull final Activity activity, @NonNull final Class<?> clz, @NonNull final Bundle options) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), options);
    }

    public static void startActivity(@NonNull final Activity activity, @NonNull final Class<?> clz, @NonNull final View... sharedElements) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), getOptionsBundle(activity, sharedElements));
    }

    public static void startActivity(@NonNull final Activity activity, @NonNull final Class<?> clz, @NonNull final int enterAnim, @NonNull final int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            activity.overridePendingTransition(enterAnim, exitAnim);
    }

    //===============================================================================
    public static void startActivity(@NonNull final Bundle extras, @NonNull final Class<?> clazz) {
        Context context = getActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clazz.getName(), null);

    }
//    ...end

    //===============================================================================
    public static void startActivity(@NonNull final String pkg, @NonNull final String cls) {
        startActivity(getActivityOrApp(), null, pkg, cls, null);
    }
//    ...end

    //===============================================================================
    public static void startActivity(@NonNull Intent intent) {
        startActivity(intent, getActivityOrApp(), null);
    }
//    ...end

    public static void startHomeActivity() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public static Bundle getOptionsBundle(@NonNull final Context context, @NonNull final int enterAnim, @NonNull final int exitAnim) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim);
        return activityOptionsCompat.toBundle();
    }

    public static Bundle getOptionsBundle(@NonNull final Activity activity, @NonNull final View[] sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int length = sharedElements.length;
            @SuppressWarnings("unchecked")
            Pair<View, String>[] pairs = new Pair[length];
            for (int i = 0; i < length; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null, null).toBundle();
    }

    public static List<Activity> getActivityList() {
        return Util.sActivityList;
    }

    public static String getLauncherActivity() {
        return getLauncherActivity(Util.getApp().getPackageName());
    }

    public static String getLauncherActivity(String pkg) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = Util.getApp().getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo ri :
                resolveInfos) {
            if (ri.activityInfo.packageName.equals(pkg)) {
                return ri.activityInfo.name;
            }
        }

        return "no " + pkg;
    }

    //===============================================================================
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);

    }

    public static void finishActivity(@NonNull final Activity activity, @NonNull final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    public static void finishActivity(@NonNull final Activity activity, @NonNull final int enterAnim, @NonNull final int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);

    }

    public static void finishActivity(@NonNull final Class<?> clz) {
        finishActivity(clz, false);
    }

    public static void finishActivity(@NonNull final Class<?> clz, @NonNull final boolean isLoadAnim) {
        List<Activity> activityList = Util.sActivityList;
        for (Activity activity : activityList) {
            if (activity.getClass().equals(clz)) {
                if (!isLoadAnim) {
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    public static void finishActivity(@NonNull final Class<?> clz, @NonNull final int enterAnim, @NonNull final int exitAnim) {
        List<Activity> activityList = Util.sActivityList;
        for (Activity activity : activityList) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        }
    }

    public static void finishOtherActivities(@NonNull final Class<?> clz, @NonNull final boolean isLoadAnim) {
        List<Activity> activityList = Util.sActivityList;
        boolean flag = false;
        for (Activity activity :
                activityList) {
            if (activity.getClass().equals(clz)) {
                if (flag) {

                    finishActivity(activity, isLoadAnim);
                } else {
                    flag = true;
                }
            } else {
                finishActivity(activity, isLoadAnim);
            }
        }
    }


    public static void finishOtherActivities(@NonNull final Class<?> clz, @NonNull final int enterAnim, @NonNull final int exitAnim) {
        List<Activity> activityList = Util.sActivityList;
        boolean flag = false;
        for (Activity activity :
                activityList) {
            if (activity.getClass().equals(clz)) {
                if (flag) {

                    finishActivity(activity, enterAnim, exitAnim);
                } else {
                    flag = true;
                }
            } else {
                finishActivity(activity, enterAnim, exitAnim);
            }
        }
    }

    public static void finishAllActivities(@NonNull final boolean isLoadAnim) {
        List<Activity> activityList = Util.sActivityList;
        boolean flag = false;
        for (Activity activity : activityList) {
            finishActivity(activity, isLoadAnim);
        }
    }

    public static void finishAllActivities(@NonNull final int enterAnim, @NonNull final int exitAnim) {
        List<Activity> activityList = Util.sActivityList;
        boolean flag = false;
        for (Activity activity : activityList) {
            finishActivity(activity, enterAnim, exitAnim);
        }
    }

    public static Drawable getActivityIcon(@NonNull final Class<?> clz) {
        return getActivityIcon(new ComponentName(Util.getApp(), clz));
    }

    public static Drawable getActivityIcon(@NonNull final ComponentName activityName) {
        PackageManager pm = Util.getApp().getPackageManager();
        try {
            return pm.getActivityIcon(activityName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getActivityLogo(@NonNull final Class<?> clz){
        return getActivityLogo(new ComponentName(Util.getApp(),clz));
    }
    public static Drawable getActivityLogo(@NonNull final ComponentName activityName){
        PackageManager pm = Util.getApp().getPackageManager();
        try {
            return pm.getActivityLogo(activityName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}