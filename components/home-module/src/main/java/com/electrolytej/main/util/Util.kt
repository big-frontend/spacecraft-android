package com.electrolytej.main.util

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference

class Util {
    companion object{
        private var i:Util?=null
        @JvmName("getI1")
        @Synchronized
        fun  getI():Util{
            if(i==null){
                i = Util()
            }
            return i!!
        }
    }
    var topActivity: WeakReference<Activity>? = null

    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                topActivity = WeakReference(activity)

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