package com.jamesfchen.common.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.Nullable;

public final class ProcessUtil {
    public static String isMainProcess(){
        Utils.getApp().getPackageName().equals(getMyProcessName());
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

}
