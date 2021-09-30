package com.jamesfchen.loader.monitor

import android.app.Activity
const val TAG_FRAME_MONITOR = "frame-monitor"
class FrameMonitor :ILifecycleObserver{
    override fun onActivityForeground(activity: Activity) {
        super.onActivityForeground(activity)
        FrameTrace0.start(activity)
        FrameTrace1.start(activity)
        FrameTrace2.start()
    }

    override fun onActivityBackground(activity: Activity) {
        super.onActivityBackground(activity)
        FrameTrace0.stop(activity)
        FrameTrace1.stop(activity)
        FrameTrace2.stop()
    }
}