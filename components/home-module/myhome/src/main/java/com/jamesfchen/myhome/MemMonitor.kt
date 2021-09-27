package com.jamesfchen.myhome

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.jamesfchen.common.util.MemoryUtil
import java.lang.ref.WeakReference

class MemMonitor {
    companion object {
        var refActivity: WeakReference<Activity>? = null
        var app:Application?=null
        var context:Application?=null
        var cxt:Context?=null
        fun init(app: Application) {
            this.app = app
            context = app.applicationContext as Application?
            cxt = app
            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    refActivity = WeakReference(activity)
                    MemoryUtil.printAppMemory(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                }

                override fun onActivityResumed(activity: Activity) {
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

}