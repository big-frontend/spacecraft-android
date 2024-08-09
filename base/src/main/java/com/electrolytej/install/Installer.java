package com.electrolytej.install;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.blankj.utilcode.util.AppUtils;
import com.electrolytej.util.Util;
import java.util.Map;
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
            if (intent == null || intent.getData() == null || !Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
                return;
            }
            String packageName = intent.getData().getEncodedSchemeSpecificPart();
            for (Map.Entry<String, OnInstallListener> entry : mInstallListeners.entrySet()) {
                String url = entry.getKey();
                OnInstallListener listener = entry.getValue();
                listener.onInstall();
            }
            unregisterInstallReceiver();
        }
    }

    public void registerInstallReceiver() {
        if (mApkInstallReceiver == null) mApkInstallReceiver = new ApkInstallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        Util.getApp().registerReceiver(mApkInstallReceiver, intentFilter);

    }

    public void unregisterInstallReceiver() {
        if (mApkInstallReceiver != null) {
            Util.getApp().unregisterReceiver(mApkInstallReceiver);
            mApkInstallReceiver = null;
        }
    }

    private Map<String, OnInstallListener> mInstallListeners = new ConcurrentHashMap<>();

    public interface OnInstallListener {
        default void onInstall() {
        }
    }

    public void addOnInstallListener(String url, OnInstallListener downloadListener) {
        mInstallListeners.put(url, downloadListener);
    }

    public void installApk(String filePath) {
        registerInstallReceiver();
        AppUtils.installApp(filePath);
    }
}
