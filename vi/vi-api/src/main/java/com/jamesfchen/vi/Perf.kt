package com.jamesfchen.vi

import android.app.Application
import android.content.Context
import com.jamesfchen.vi.lifecycle.initLifecycle
import com.jamesfchen.vi.lifecycle.registerLifecycle
import com.jamesfchen.vi.render.FpsItem
import com.jamesfchen.vi.startup.StartupItem
import com.jamesfchen.vi.startup.TAG_STARTUP_MONITOR
import com.jamesfchen.vi.util.TraceUtil
import lab.galaxy.yahfa.HookMain

object Perf {
    internal lateinit var app: Application
    var inited = false
    private fun register() {
        registerLifecycle(StartupItem::class.java)
        registerLifecycle(FpsItem::class.java)
        registerLifecycle(MemItem::class.java)
    }

    @JvmStatic
    fun attachBaseContext(base:Context) {
        //只监测主进程性能
        HookMain.doHookDefault(base.classLoader)
        ActivityThreadHacker.hackSysHandlerCallback()
        TraceUtil.i(TAG_STARTUP_MONITOR,"PerfApp#attachBaseContext");
    }

    @JvmStatic
     fun onCreate(application: Application) {
        if (inited) return
        //由于ProcessLifecycleOwnerInitializer multiprocess=true，这样每个进程都会有一个ContentProvider对象，
        //进程使用ContentProvider不用通过framework跨进程获取，自己进程本身就有一个，这样能提升性能，但是也增加了进程开销
        app = application
        try {
            DebugOverlayController.requestPermission(app)
        } catch (ignore: Exception) {
        }
        initLifecycle(application)
        register()
        inited = true
    }


}