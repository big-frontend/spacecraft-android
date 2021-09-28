package com.jamesfchen.loader.monitor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle

const val TAG_APM_MONITOR = "app-monitor"

class Monitor(val app: Application) {
    companion object {
        lateinit var app:Application
        fun init(p: Application) {
            app = p
            Monitor(p).start()
        }
    }

    @SuppressLint("NewApi")
    private fun start() {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                MemoryUtil.printAppMemory(activity)
            }

            //前台
            override fun onActivityStarted(activity: Activity) {
                FrameTrace0.start(activity)
                FrameTrace1.start(activity)
                FrameTrace2.start()

            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            //后台
            override fun onActivityStopped(activity: Activity) {
                FrameTrace0.stop(activity)
                FrameTrace1.stop(activity)
                FrameTrace2.stop()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }


}