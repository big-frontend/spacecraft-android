package com.hawksjamesf.common.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
public class ProessUtil {
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
