package com.electrolytej.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ProcessUtil {
    public static boolean isMainProcess(){
        return Utils.getApp().getPackageName().equals(getMyProcessName());
    }
    @Nullable
    public static String getMyProcessName(){
        String name = getProcessNameByFile(Process.myPid());
        if (TextUtils.isEmpty(name)){
            name = getProcessNameByAms(Process.myPid());
        }
        if (TextUtils.isEmpty(name)){
            name = getMyProcessNameByReflect();
        }
        return  name;
    }
    @Nullable
    public static String getProcessNameByFile(int pid) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"))) {
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getProcessNameByAms(int pid) {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
            if (processInfo.pid == pid && processInfo.processName != null) {
                return processInfo.processName;
            }
        }
        return null;
    }

    private static String getMyProcessNameByReflect() {
        String processName = null;
        try {
            Application app = Utils.getApp();
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);

            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);

            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName");
            processName = (String) getProcessName.invoke(activityThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processName;
    }

    //获取前台线程包名
    public static String getForegroundProcessName() {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return null;
        List<ActivityManager.RunningAppProcessInfo> infors = am.getRunningAppProcesses();
        if (infors != null && infors.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo info :
                    infors) {
                if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return info.processName;
                }
            }
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = Util.getApp().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() <= 0) {
                return null;
            }

            try {
                ApplicationInfo info = pm.getApplicationInfo(Util.getApp().getPackageName(), 0);
                AppOpsManager aom = (AppOpsManager) Util.getApp().getSystemService(Context.APP_OPS_SERVICE);
                if (aom != null) {
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Util.getApp().startActivity(intent);
                    }

                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        return null;
                    }
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) Util.getApp().getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 24 * 3600000 * 7;
                    usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) return null;
                UsageStats recentStats = null;
                for (UsageStats stats :
                        usageStatsList) {
                    if (recentStats == null || stats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                    }
                    recentStats = stats;
                }

                return recentStats == null ? null : recentStats.getPackageName();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Set<String> getAllBackgroundProcess() {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<ActivityManager.RunningAppProcessInfo> infors = am.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        if (infors != null) {
            for (ActivityManager.RunningAppProcessInfo info :
                    infors) {
                Collections.addAll(set, info.pkgList);
            }
        }

        return set;
    }

    public static Set<String> killAllBackgroundProcess() {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<ActivityManager.RunningAppProcessInfo> infors = am.getRunningAppProcesses();
        Set<String> deathSet = new HashSet<>();
        if (infors != null) {
            for (ActivityManager.RunningAppProcessInfo info : infors) {
                for (String pkg : info.pkgList) {
                    am.killBackgroundProcesses(pkg);
                    deathSet.add(pkg);
                }
            }
        }

        infors = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infors) {
            for (String pkg : info.pkgList) {
                deathSet.remove(pkg);
            }
        }

        return deathSet;
    }

    public static boolean killBackgroundProcess(@NonNull final String packageNam) {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> infors = am.getRunningAppProcesses();
        if (infors == null || infors.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo info : infors) {
            if (Arrays.asList(info.pkgList).contains(packageNam)) {
                am.killBackgroundProcesses(packageNam);
            }
        }
        infors = am.getRunningAppProcesses();
        if (infors == null || infors.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo info :
                infors) {
            if (Arrays.asList(info.pkgList).contains(packageNam)) {
                return false;
            }
        }

        return true;
    }

}
