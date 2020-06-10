@file:JvmName("MemoryUtil")

package com.hawksjamesf.common.util

import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.ApplicationInfo
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 * https://stackoverflow.com/questions/2630158/detect-application-heap-size-in-android
 */
object MemoryUtil {
    val am by lazy {
        ContextCompat.getSystemService(Util.getApp(), ActivityManager::class.java)
    }
    val hasLargeHeap = Util.getApp().applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP != 0
    //This method tells you how many total bytes of heap your app is allowed to use.
    val maxMemory = Runtime.getRuntime().maxMemory();

    fun availableHeap() = if (hasLargeHeap) am?.largeMemoryClass else am?.memoryClass

    fun memo(){
        val mi = ActivityManager.MemoryInfo()
        am?.getMemoryInfo(mi)
        //1024 bytes      == 1 Kibibyte
        //1024 Kibibyte   == 1 Mebibyte
        //
        //1024 * 1024     == 1048576
        //1048576         == 0x100000
        val availableMegs = mi.availMem / 0x100000
        //Percentage can be calculated for API 16+
        val percentAvail: Double = mi.availMem / mi.totalMem.toDouble() * 100.0
    }
}