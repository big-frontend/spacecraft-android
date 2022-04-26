package com.jamesfchen.viapm

import android.app.Application
import androidx.lifecycle.*
import com.jamesfchen.viapm.tracer.FpsItem
import com.jamesfchen.viapm.tracer.StartupItem

const val TAG_VI_MONITOR = "vi-monitor"
object ViMonitor : ILifecycleObserver {
    lateinit var app: Application
    var inited = false
    fun start(application: Application){
        app = application
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        app.registerActivityLifecycleCallbacks(this)
        //由于ProcessLifecycleOwnerInitializer multiprocess=true，这样每个进程都会有一个ContentProvider对象，
        //进程使用ContentProvider不用通过framework跨进程获取，自己进程本身就有一个，这样能提升性能，但是也增加了进程开销
        ProcessLifecycleOwner.get().lifecycle.addObserver(MemItem())
        app.registerActivityLifecycleCallbacks(MemItem())
        ProcessLifecycleOwner.get().lifecycle.addObserver(FpsItem())
        app.registerActivityLifecycleCallbacks(FpsItem())
//        ProcessLifecycleOwner.get().lifecycle.addObserver(LayoutInflateItem())
//        app.registerActivityLifecycleCallbacks(LayoutInflateItem())
        ProcessLifecycleOwner.get().lifecycle.addObserver(StartupItem())
        app.registerActivityLifecycleCallbacks(StartupItem())
        inited = true
    }
}
