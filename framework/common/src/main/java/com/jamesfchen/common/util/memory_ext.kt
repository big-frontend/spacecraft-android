@file:JvmName("MemoryUtil")

package com.jamesfchen.common.util

import android.app.Activity
import android.app.ActivityManager
import android.content.pm.ApplicationInfo
import android.os.Debug
import android.util.Log
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.jamesfchen.common.constants.MemoryUnit.MB


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 * https://stackoverflow.com/questions/2630158/detect-application-heap-size-in-android
 *
 *
 * adb shell getprop dalvik.vm.heapstartsize:16m
 * adb shell getprop dalvik.vm.heapsize:512m
 * adb shell getprop dalvik.vm.heapgrowthlimit:128m
 */
object MemoryUtil {
    val am by lazy {
        ContextCompat.getSystemService(Util.getApp(), ActivityManager::class.java)
    }
    //android4.4版本以下都被认为低ram设备
    val isLowRamDevice = am?.isLowRamDevice
    val hasLargeHeap = Util.getApp().applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP != 0
    //art虚拟机可用堆的大小 单位为m
    fun availableHeap() = if (hasLargeHeap) am?.largeMemoryClass else am?.memoryClass

    //art虚拟机内存信息
    val art = Runtime.getRuntime()
    val maxMemory = art.maxMemory()
    val totalMemory = art.totalMemory()
    val freeMemory = art.freeMemory()

    val debugMi = Debug.MemoryInfo()
    val amsMi = ActivityManager.MemoryInfo()
    fun printAppMemory(page: Activity){
        Log.d("MemoryUtil","page:${page.componentName.shortClassName} ART freeMemory:${freeMemory/MB} totalMemory:${totalMemory/MB} maxMemory:${maxMemory/MB} ")
        //1024 bytes      == 1 Kibibyte
        //1024 Kibibyte   == 1 Mebibyte
        //
        //1024 * 1024     == 1048576
        //1048576         == 0x100000

        //这些内存用来判断是否每次处于low memory
        am?.getMemoryInfo(amsMi)
        Log.d("MemoryUtil","page:${page.componentName.shortClassName} " +
                "AMS availMem:${amsMi.availMem/ MB} totalMem:${amsMi.totalMem/MB} threshold:${amsMi.threshold/MB} lowMemory:${amsMi.lowMemory}")


        //下面单位都是kb
        //物理内存
        Debug.getMemoryInfo(debugMi)
        debugMi.totalPss
        debugMi.totalSwappablePss
        val dalvikPss = debugMi.dalvikPss
        val nativePss = debugMi.nativePss
        val otherPss = debugMi.otherPss

//        am.getProcessMemoryInfo()
    }
}