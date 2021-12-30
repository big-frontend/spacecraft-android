package com.jamesfchen.main

import android.app.Activity
import android.app.ActivityManager
import android.os.Bundle
import android.util.Log
import com.jamesfchen.loader.SApp
import com.jamesfchen.loader.systemfilter.SystemFilter

class HotelApp : SApp() {
    override fun onCreate() {
        super.onCreate()
        SystemFilter.applyGrayMode()
        val activityManager: ActivityManager = getSystemService(ActivityManager::class.java)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {


            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                val tasks: List<ActivityManager.AppTask> = activityManager.appTasks
                Log.d("cjf", "tasks size:" + tasks.size)
                tasks.forEachIndexed { index, appTask ->
                    val info = appTask.taskInfo
                    Log.d("cjf", "${index}[${info.id}/${ info.affiliatedTaskId}] ${info.taskDescription}")
                    Log.d("cjf", "${index}[${info.id}/${ info.affiliatedTaskId}] activity size:${ info.numActivities}")
                    Log.d("cjf", "${index}[${info.id}/${ info.affiliatedTaskId}] top activity :${info.topActivity?.className}")
                    Log.d("cjf", "${index}[${info.id}/${ info.affiliatedTaskId}] bottom activity :${info.baseActivity?.className}")
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
    }
}