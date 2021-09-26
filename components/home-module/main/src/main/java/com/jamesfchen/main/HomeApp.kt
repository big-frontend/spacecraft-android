package com.jamesfchen.main

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Debug
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.jamesfchen.loader.App
import com.jamesfchen.myhome.MemMonitor
import com.jamesfchen.myhome.util.Util

@com.jamesfchen.lifecycle.App
class HomeApp : App() {
    override fun onCreate() {
        super.onCreate()
        Util.getI().init(this)
        MemMonitor.init(this)
        val activityManager: ActivityManager = getSystemService(ActivityManager::class.java)
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                val tasks: List<ActivityManager.AppTask> = activityManager.appTasks
                Log.d("cjf", "tasks size:" + tasks.size)
                tasks.forEachIndexed { index, appTask ->
                    val info = appTask.taskInfo
                    Log.d(
                        "cjf",
                        "${index}[${info.id}/${info.affiliatedTaskId}] ${info.taskDescription}"
                    )
                    Log.d(
                        "cjf",
                        "${index}[${info.id}/${info.affiliatedTaskId}] activity size:${info.numActivities}"
                    )
                    Log.d(
                        "cjf",
                        "${index}[${info.id}/${info.affiliatedTaskId}] top activity :${info.topActivity?.className}"
                    )
                    Log.d(
                        "cjf",
                        "${index}[${info.id}/${info.affiliatedTaskId}] bottom activity :${info.baseActivity?.className}"
                    )
//                    appTask.setExcludeFromRecents(true); //不显示在最近任务列表中
//                    appTask.finishAndRemoveTask(); //销毁任务栈内全部Activity
                }
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
        Log.d(
            "cjf",
            "has sd permission ${hasSdPermission()}  ${Environment.getExternalStorageDirectory()}"
        )

    }

    fun hasSdPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        //                && ContextCompat.checkSelfPermission(sApp, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {

            Log.d("cjf", "App#onTrimMemory }")
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("cjf", "App#onLowMemory }")
    }
}
