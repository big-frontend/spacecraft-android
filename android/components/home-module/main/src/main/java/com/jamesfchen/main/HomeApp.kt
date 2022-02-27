//package com.jamesfchen.main
//
//import android.Manifest
//import android.app.Activity
//import android.app.ActivityManager
//import android.app.Application
//import android.content.ComponentCallbacks2
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.os.Debug
//import android.os.Environment
//import android.util.Log
//import androidx.core.content.ContextCompat
//import androidx.core.content.ContextCompat.getSystemService
//import com.jamesfchen.loader.SApp
//import com.jamesfchen.myhome.util.Util
//
//@com.jamesfchen.lifecycle.App
//class HomeApp : SApp() {
//    override fun onCreate() {
//        super.onCreate()
//        Util.getI().init(this)
//        val activityManager: ActivityManager = getSystemService(ActivityManager::class.java)
//        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
//            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//            }
//
//            override fun onActivityStarted(activity: Activity) {
//
//            }
//
//            override fun onActivityResumed(activity: Activity) {
//                val tasks: List<ActivityManager.AppTask> = activityManager.appTasks
//                Log.d("cjf", "tasks size:" + tasks.size)
//                tasks.forEachIndexed { index, appTask ->
//                    val info = appTask.taskInfo
//                    Log.d(
//                        "cjf",
//                        "${index}[${info.id}/${info.affiliatedTaskId}] ${info.taskDescription}"
//                    )
//                    Log.d(
//                        "cjf",
//                        "${index}[${info.id}/${info.affiliatedTaskId}] activity size:${info.numActivities}"
//                    )
//                    Log.d(
//                        "cjf",
//                        "${index}[${info.id}/${info.affiliatedTaskId}] top activity :${info.topActivity?.className}"
//                    )
//                    Log.d(
//                        "cjf",
//                        "${index}[${info.id}/${info.affiliatedTaskId}] bottom activity :${info.baseActivity?.className}"
//                    )
////                    appTask.setExcludeFromRecents(true); //不显示在最近任务列表中
////                    appTask.finishAndRemoveTask(); //销毁任务栈内全部Activity
//                }
//            }
//
//            override fun onActivityPaused(activity: Activity) {
//            }
//
//            override fun onActivityStopped(activity: Activity) {
//            }
//
//            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//            }
//
//            override fun onActivityDestroyed(activity: Activity) {
//            }
//        })
//        Log.d(
//            "cjf",
//            "has sd permission ${hasSdPermission()}  ${Environment.getExternalStorageDirectory()}"
//        )
//
//    }
//
//    fun hasSdPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ) == PackageManager.PERMISSION_GRANTED
//        //                && ContextCompat.checkSelfPermission(sApp, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//    }
//
//    override fun onTrimMemory(level: Int) {
//        super.onTrimMemory(level)
//        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
//
//            Log.d("cjf", "App#onTrimMemory")
//        }
//        // 根据不同的应用生命周期和系统事件进行不同的操作
//        when (level) {
//
//            // 应用界面处于后台
//            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
//                // 可以在这里释放 UI 对象
//            }
//
//            // 应用正常运行中，不会被杀掉，但是系统内存已经有点低了
//            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
//
//                // 应用正常运行中，不会被杀掉，但是系统内存已经非常低了，
//                // 这时候应该释放一些不必要的资源以提升系统性能
//            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
//
//                // 应用正常运行，但是系统内存非常紧张，
//                // 系统已经开始根据 LRU 缓存杀掉了大部分缓存的进程
//                // 这时候我们要释放所有不必要的资源，不然系统可能会继续杀掉所有缓存中的进程
//            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
//                // 释放资源
//            }
//
//            // 系统内存很低，系统准备开始根据 LRU 缓存清理进程，
//            // 这时我们的程序在 LRU 缓存列表的最近位置，不太可能被清理掉，
//            // 但是也要去释放一些比较容易恢复的资源，让系统内存变得充足
//            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND,
//
//                // 系统内存很低，并且我们的应用处于 LRU 列表的中间位置，
//                // 这时候如果还不释放一些不必要资源，那么我们的应用可能会被系统干掉
//            ComponentCallbacks2.TRIM_MEMORY_MODERATE,
//
//                // 系统内存非常低，并且我们的应用处于 LRU 列表的最边缘位置，
//                // 系统会有限考虑干掉我们的应用，如果想活下来，就要把所有能释放的资源都释放了
//            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
//                /*
//                 * 把所有能释放的资源都释放了
//                */
//            }
//
//            // 应用从系统接收到一个无法识别的内存等级值，
//            // 跟一般的低内存消息提醒一样对待这个事件
//            else -> {
//                // 释放所有不重要的数据结构。
//            }
//        }
//
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        Log.d("cjf", "App#onLowMemory")
//    }
//}
