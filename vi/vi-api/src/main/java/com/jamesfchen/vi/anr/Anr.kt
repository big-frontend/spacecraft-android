package com.jamesfchen.vi.anr

import android.app.Application
import androidx.annotation.Keep
import com.jamesfchen.vi.lifecycle.AbsActivitiesLifecycleObserver
import com.jamesfchen.vi.lifecycle.AbsAppLifecycleObserver

const val TAG_ANR_MONITOR = "anr-monitor"
@Keep
class AnrItem(val app: Application) : AbsActivitiesLifecycleObserver(), AbsAppLifecycleObserver {
    private val messageTracker = MessageTracker()
    override fun onAppCreate() {
        messageTracker.start()
    }
}