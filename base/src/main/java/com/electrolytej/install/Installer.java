package com.electrolytej.install;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.electrolytej.util.Util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Installer {
    public static Installer getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        public static Installer instance = new Installer();
    }

    private ApkInstallReceiver mApkInstallReceiver;

    private Installer() {
    }


    class ApkInstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getData() == null) {
                return;
            }
            String action = intent.getAction();
            String packageName = intent.getData().getEncodedSchemeSpecificPart();
            if (StringUtils.isEmpty(packageName) || mInstallListeners.isEmpty()) {
                return;
            }
//            Log.d(TAG, "action：" + action + " key:" + packageName);
            Iterator<Map.Entry<String, Set<OnInstallListener>>> iterator = mInstallListeners.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Set<OnInstallListener>> entry = iterator.next();
                String pkg = entry.getKey();
//                Log.d(TAG, "action：" + action + " packageName:" + key + " " + packageName);
                Set<OnInstallListener> set = entry.getValue();
                if (set == null || set.isEmpty()) continue;

                for (OnInstallListener listener : set) {
                    if (packageName.equals(pkg)) {
                        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
                            listener.onPackageAdded(packageName);
                        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
                            listener.onPackageRemoved(packageName);
                        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
                            listener.onPackageReplaced(packageName);
                        }
                    }
                }

                //上报埋点
                OnInstallListener listener = set.iterator().next();
                if (Intent.ACTION_PACKAGE_ADDED.equals(action) && packageName.equals(pkg)) {
                    listener.onReportPackageAdded(packageName);
                }
            }
//            unregisterInstallReceiver();
        }
    }

    public void registerInstallReceiver() {
        if (mApkInstallReceiver == null) mApkInstallReceiver = new ApkInstallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");
        Util.getApp().registerReceiver(mApkInstallReceiver, intentFilter);

    }

    public void unregisterInstallReceiver() {
        if (mApkInstallReceiver != null) {
            Util.getApp().unregisterReceiver(mApkInstallReceiver);
            mApkInstallReceiver = null;
        }
    }

    private Map<String, Set<OnInstallListener>> mInstallListeners = new ConcurrentHashMap<>();

    public interface OnInstallListener {

        default void onPackageAdded(String packageName) {
        }

        default void onPackageRemoved(String packageName) {
        }


        default void onPackageReplaced(String packageName) {
        }

        default void onReportPackageAdded(String packageName) {
        }

    }

    public void addOnInstallListener(String packageName, OnInstallListener listener) {
        if (packageName == null || packageName.isEmpty() || listener == null) return;
        Set<OnInstallListener> l = mInstallListeners.get(packageName);
        if (l == null) l = new LinkedHashSet<>();
        l.add(listener);
        mInstallListeners.put(packageName.trim(), l);
    }

    public void removeOnInstallListener(String packageName, OnInstallListener onInstallListener) {
        if (packageName == null || packageName.isEmpty()) return;
        Set<OnInstallListener> set = mInstallListeners.get(packageName);
        if (set == null) return;
        Iterator<OnInstallListener> iterator = set.iterator();
        while (iterator.hasNext()) {
            OnInstallListener listener = iterator.next();
            if (onInstallListener == listener) {
                iterator.remove();
            }
        }
    }

    public boolean installApk(String filePath, String packageName) {
        if (TextUtils.isEmpty(packageName)) return false;
        if (AppUtils.isAppInstalled(packageName)) {
            return true;
        }
        AppUtils.installApp(filePath);
        return true;
    }
}
