package com.jamesfchen.loader.monitor

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import com.jamesfchen.lifecycle.AppLifecycle
import java.lang.ref.WeakReference
import java.util.ArrayList

const val TAG_MEM_MONITOR = "mem-monitor"
@AppLifecycle
class MemMonitor : ILifecycleObserver {
    companion object {
//        @GuardedBy("lock")
//        private var sInstance: MemMonitor? = null
//        private val lock = Any()
//        fun getInstance(): MemMonitor {
//            if (sInstance == null) {
//                synchronized(lock) {
//                    if (sInstance == null) {
//                        sInstance = MemMonitor()
//                    }
//                }
//            }
//            return sInstance!!
//        }

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


    }

    private var i = 0

    override fun onAppCreate() {
        super.onAppCreate()
        addGcWatcher {
            i += 1
            Log.d(TAG_MEM_MONITOR, "总计发生${i}次gc，应用触发${j}次gc")
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        MemoryUtil.printAppMemory(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        super.onActivityDestroyed(activity)
        Log.d(TAG_MEM_MONITOR, "finish activity:${activity}")
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

    class MemAnalyzer {

    }
}