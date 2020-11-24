package com.hawksjamesf.yposedplugin;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: May/23/2020  Sat
 */
public class Hook_PackageManager_getPackageInfo {
    public static String className = "android.app.ApplicationPackageManager";
    public static String methodName = "getPackageInfo";
    public static String methodSig = "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;";
    public static PackageInfo hook(PackageManager thiz, String name, int i) {
        Log.d("cjf", "hook getPackageInfo");
        return null;
    }

    public static PackageInfo backup(PackageManager thiz, String name, int i) {
        return null;
    }
}
