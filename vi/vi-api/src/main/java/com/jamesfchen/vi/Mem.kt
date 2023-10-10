package com.jamesfchen.vi

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.MessageQueue.IdleHandler
import android.os.SystemClock
import android.util.Log
import androidx.annotation.Keep
import com.jamesfchen.vi.lifecycle.AbsActivitiesLifecycleObserver
import com.jamesfchen.vi.lifecycle.AbsAppLifecycleObserver
import com.jamesfchen.vi.util.CpuUtil
import com.jamesfchen.vi.util.DeviceUtil
import com.jamesfchen.vi.util.MemoryUtil.getAppMemoryInfo
import java.lang.ref.WeakReference

/**
 * 内存监控专项
 * - 监控内存泄露
 * - 监控大图
 * -
 */
const val TAG_MEM_MONITOR = "mem-monitor"
@Keep
class MemItem(val app: Application) : AbsActivitiesLifecycleObserver(), AbsAppLifecycleObserver {
    val leakMemoryItem = LeakMemoryItem()
    val bigBitmapItem = BigBitmapItem()
    lateinit var topActivityRef:WeakReference<Activity>
    private var mActiveActivityCount = 0
    override fun onAppCreate() {
        leakMemoryItem.start()
        bigBitmapItem.start()
        Log.e(TAG_MEM_MONITOR, "onAppCreate")
    }
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        mActiveActivityCount++
        Log.e(TAG_MEM_MONITOR, "onActivityCreated ${mActiveActivityCount}")
    }
    override fun onActivityForeground(activity: Activity) {
        Log.e(TAG_MEM_MONITOR, "onActivityForeground activity:${activity}")
        topActivityRef = WeakReference(activity)
    }

    override fun onActivityWindowFocusChanged(activity: Activity, hasFocus: Boolean) {
        Log.e(TAG_MEM_MONITOR, "onActivityWindowFocusChanged activity:${activity}")
    }

    override fun onActivityBackground(activity: Activity) {
        Log.e(TAG_MEM_MONITOR, "onActivityBackground activity:${activity}")
    }
    override fun onActivityDestroyed(activity: Activity) {
        mActiveActivityCount--
        Log.e(TAG_MEM_MONITOR, "onActivityDestroyed ${mActiveActivityCount}")
        leakMemoryItem.checkLeakEvent()
        if (mActiveActivityCount == 0){
            leakMemoryItem.reset()
        }
    }

}

class LeakMemoryItem {
    companion object {
        var sLastGcTime: Long = 0
        var sGcWatcher = WeakReference(GcWatcher())
        var sGcWatchers = ArrayList<Runnable>()
        var sTmpWatchers = arrayOfNulls<Runnable>(1)
        fun addGcWatcher(watcher: Runnable) {
            synchronized(sGcWatchers) {
                sGcWatchers.add(watcher)
            }
        }
        private var j = 0
    }

    private var i = 0
    fun start() {
        addGcWatcher {
            i += 1
            Log.e(TAG_MEM_MONITOR, "总计发生${i}次gc，应用触发${j}次gc")
            Log.e(TAG_MEM_MONITOR, ">>> device level:" + DeviceUtil.getLevel())
            Log.e(TAG_MEM_MONITOR, CpuUtil.getAppCpuInfo())
            Log.e(TAG_MEM_MONITOR, getAppMemoryInfo())
        }
    }

    fun forceGc(reason: String) {
//            System.gc()
        j += 1
        Runtime.getRuntime().gc()
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            Log.e(TAG_MEM_MONITOR, Log.getStackTraceString(e))
        }
        Runtime.getRuntime().runFinalization()
    }
    fun reset(){
        i = 0
        j = 0
    }

    fun checkLeakEvent() {
        forceGc("")
    }

    /**
     * 当应用force gc时，只是建议系统要gc，由于一些客观因素系统不一定会gc，既然应用force gc不能马上就响应，
     * 那么就可以通过GcWatcher监听系统正在gc，对此做出应用自己的处理
     */
    class GcWatcher {
        @Throws(Throwable::class)
        protected fun finalize() {
//            handleGc();
            sLastGcTime = SystemClock.uptimeMillis()
            synchronized(sGcWatchers) {
                sTmpWatchers = sGcWatchers.toArray<Runnable>(sTmpWatchers)//sTmpWatchers默认为1，不足自动扩容
            }
            for (i in sTmpWatchers.indices) {
                sTmpWatchers[i]?.run()
            }
            sGcWatcher = WeakReference(GcWatcher())
        }
    }

    internal class GcIdler : IdleHandler {
        override fun queueIdle(): Boolean {
//            forceGc("")
//            doGcIfNeeded()
            return false
        }
    }

    fun doGcIfNeeded() {
//        mGcIdlerScheduled = false
//        val now = SystemClock.uptimeMillis()
//        //Slog.i(TAG, "**** WE MIGHT WANT TO GC: then=" + Binder.getLastGcTime()
//        //        + "m now=" + now);
//        if (BinderInternal.getLastGcTime() + android.app.ActivityThread.MIN_TIME_BETWEEN_GCS < now) {
//            //Slog.i(TAG, "**** WE DO, WE DO WANT TO GC!");
//            BinderInternal.forceGc("bg")
//        }
    }

    class MemAnalyzer {

    }
}

class BigBitmapItem {
    fun start() {
        //todo:start hook art
    }
}