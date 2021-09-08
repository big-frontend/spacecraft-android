package com.jamesfchen.loader.monitor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.jamesfchen.loader.monitor.FrameTrace0
import com.jamesfchen.loader.monitor.FrameTrace1
import com.jamesfchen.loader.monitor.FrameTrace2

const val TAG_APM_MONITOR = "app-monitor"

class Monitor(val app: Application) {
    companion object {
        fun init(app: Application) {
            Monitor(app).start()
        }
    }

    @SuppressLint("NewApi")
    private fun start() {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            //前台
            override fun onActivityStarted(activity: Activity) {
                FrameTrace0.start(activity)
                FrameTrace1.start()
                FrameTrace2.start()

            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            //后台
            override fun onActivityStopped(activity: Activity) {
                FrameTrace0.stop(activity)
                FrameTrace1.stop()
                FrameTrace2.stop()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }


}