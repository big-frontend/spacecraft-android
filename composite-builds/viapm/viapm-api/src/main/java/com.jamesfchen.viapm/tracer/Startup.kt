package com.jamesfchen.viapm.tracer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.ArrayMap
import android.util.Log
import com.blankj.utilcode.util.ReflectUtils
import com.jamesfchen.lifecycle.AppLifecycle
import com.jamesfchen.lifecycle.IAppLifecycleObserver
import com.jamesfchen.viapm.ViMonitor

/**
 *
 * 如果启动过程中存在转场动画需要扣除
 * app start up
 * - 冷启动
 * - 热启动
 *
 * activity start up
 *  - 页面最早渲染出2个文本控件的时间(tti)
 *  - onCreate 到 onWindowFocusChanged 的时间
 *
 *  service start up
 *
 *  ContentProvider start up
 *  - App#attachBaseContext --> ContentProvider#attachInfo --> ContentProvider#onCreate--->App#onCreate
 */
const val TAG_STARTUP_MONITOR = "startup-monitor"

@AppLifecycle
class StartupItem : IAppLifecycleObserver {
    override fun onAppCreate() {
        super.onAppCreate()
    }

    var alreadyCheck = false

    @SuppressLint("LogNotTimber")
    override fun onAppForeground() {
        super.onAppForeground()
        if (!alreadyCheck) {
            for (pack in ViMonitor.app.packageManager
                .getInstalledPackages(PackageManager.GET_PROVIDERS)) {
                val providers = pack.providers ?: continue
                for (provider in providers) {
                    if (provider.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                        if (provider.packageName == ViMonitor.app.packageName) {
//                            Log.d(
//                                TAG_STARTUP_MONITOR,
//                                "当前provider package: " + provider.packageName + " authority: " + provider.authority + " name: " + provider.name
//                            )
                        } else {
//                        Log.d("cjf", "第三方provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);
                        }
                    } else {
//                    Log.d("cjf", "系统provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);
                    }
                }
            }
            alreadyCheck = true
            val at =
                ReflectUtils.reflect("android.app.ActivityThread").method("currentActivityThread")
                    .get<Any>()
            val mProviderMap: ArrayMap<Any, Any>? =
                ReflectUtils.reflect(at).field("mProviderMap").get<ArrayMap<Any, Any>>()
            val mProviderRefCountMap: ArrayMap<IBinder, Any>? =
                ReflectUtils.reflect(at).field("mProviderRefCountMap").get<ArrayMap<IBinder, Any>>()
            val mLocalProvidersByName: ArrayMap<ComponentName, Any>? =
                ReflectUtils.reflect(at).field("mLocalProvidersByName")
                    .get<ArrayMap<ComponentName, Any>>()
//            val mLocalProviders: ArrayMap<Any, Any>? =
//                ReflectUtils.reflect(at).field("mLocalProviders").get<ArrayMap<Any, Any>>()
            if (mLocalProvidersByName != null && mLocalProvidersByName.isNotEmpty()) {
                mLocalProvidersByName.forEach { entry ->
                    Log.d(TAG_STARTUP_MONITOR, "local provider:${entry.key}")
                }
            } else {
                Log.e(TAG_STARTUP_MONITOR, "mLocalProvidersByName：没有数据")
            }

            if (mProviderRefCountMap != null && mProviderRefCountMap.isNotEmpty()) {
                mProviderRefCountMap.forEach { entry ->
                    Log.d(
                        TAG_STARTUP_MONITOR,
                        "mProviderRefCountMap：${entry.value::class.java.canonicalName}"
                    )
//                    val holder= ReflectUtils.reflect(entry.value).field("holder").get<`Object`>()
//                    val info = ReflectUtils.reflect(holder).field("info").get<ProviderInfo>()
//                    Log.d(TAG_STARTUP_MONITOR, "remote provider:${info.name}")
                }
            } else {
                Log.e(TAG_STARTUP_MONITOR, "mProviderRefCountMap：没有数据")
            }
        }
    }
}

class AppStartup {}
class ActivityStartup {}