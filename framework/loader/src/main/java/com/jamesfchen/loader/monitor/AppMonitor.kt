package com.jamesfchen.loader.monitor

import android.app.Application
import androidx.lifecycle.*
import com.jamesfchen.lifecycle.App

const val TAG_APM_MONITOR = "app-monitor"
@App
object AppMonitor : ILifecycleObserver{
    lateinit var app: Application
    var inited = false
    fun start(application: Application){
        app = application
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        app.registerActivityLifecycleCallbacks(this)
        //由于ProcessLifecycleOwnerInitializer multiprocess=true，这样每个进程都会有一个ContentProvider对象，
        //进程使用ContentProvider不用通过framework跨进程获取，自己进程本身就有一个，这样能提升性能，但是也增加了进程开销
        ProcessLifecycleOwner.get().lifecycle.addObserver(MemMonitor())
        app.registerActivityLifecycleCallbacks(MemMonitor())
//        ProcessLifecycleOwner.get().lifecycle.addObserver(FrameMonitor())
//        app.registerActivityLifecycleCallbacks(FrameMonitor())
        inited = true
    }
}