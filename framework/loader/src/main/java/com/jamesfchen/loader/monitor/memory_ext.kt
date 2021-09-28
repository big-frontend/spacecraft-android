@file:JvmName("MemoryUtil")

package com.jamesfchen.loader.monitor

import android.app.Activity
import android.app.ActivityManager
import android.content.pm.ApplicationInfo
import android.os.Debug
import android.os.Process
import android.util.Log
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt


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
 *
 *1024 bytes      == 1 Kibibyte
 *1024 Kibibyte   == 1 Mebibyte
 *
 *1024 * 1024     == 1048576
 *1048576         == 0x100000
 *
 *进程中的heap 分为java heap(java new 指令创建的对象) 与 native heap(c/cpp new指令与malloc函数 创建的对象)
 *
 * java heap常常发生OutOfMemory
 *
 */
object MemoryUtil {
    val am by lazy {
        ContextCompat.getSystemService(Monitor.app, ActivityManager::class.java)
    }

    //android4.4版本以下都被认为低ram设备
    val isLowRamDevice = am?.isLowRamDevice
    val hasLargeHeap = Monitor.app.applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP != 0

    //memoryClass为普通app的heap单位为m
    fun availableHeap() = if (hasLargeHeap) am?.largeMemoryClass else am?.memoryClass

    data class HeapInfo(val maxKb: Long, val freeKb: Long, val allocatedKb: Long)

    //App虚拟机堆内存
    fun getAppHeapInfo(): HeapInfo {
        val art = Runtime.getRuntime()
        //没有开启large heap时 maxMemory:384m ,开启large heap时 maxMemory:512m,app使用了大顶堆
        val maxMemKb = art.maxMemory() / 1024
        val freeMemKb = art.freeMemory() / 1024
        val allocatedKb = (art.totalMemory() - art.freeMemory()) / 1024
        return HeapInfo(maxMemKb, freeMemKb, allocatedKb)
    }

    data class PssInfo(
        val totalPssKb: Int,
        val dalvikPssKb: Int,
        val nativePssKb: Int,
        val otherPssKb: Int
    )

    //App物理内存
    fun getAppPssInfo(): PssInfo {
        val pid = Process.myPid()
        am?.getProcessMemoryInfo(intArrayOf(pid))?.get(0)?.let { pssInfo ->
            return PssInfo(pssInfo.totalPss, pssInfo.dalvikPss, pssInfo.nativePss, pssInfo.otherPss)
        } ?: let {
            val pssInfo = Debug.MemoryInfo()
//            pssInfo.totalSwappablePss
            Debug.getMemoryInfo(pssInfo)
            return PssInfo(pssInfo.totalPss, pssInfo.dalvikPss, pssInfo.nativePss, pssInfo.otherPss)
        }
    }

    data class RamInfo(
        val availMemKb: Long = 0,
        val isLowMemory: Boolean = true,
        val lowMemThresholdKb: Long = 0,
        val totalMemKb: Long = 0 //4.4之后直接通过ActivityManager获取，之前使用进程的meminfo文件
    )

    fun getRamInfo(): RamInfo {
        val m = ActivityManager.MemoryInfo()
        am?.getMemoryInfo(m)
        return RamInfo(m.availMem / 1024, m.lowMemory, m.threshold / 1024, m.totalMem / 1024)

    }

    fun printAppMemory(page: Activity) {
        val (availMemKb, isLowMemory, lowMemThresholdKb, totalMemKb) = getRamInfo()
        val (totalPssKb,
            dalvikPssKb,
            nativePssKb,
            otherPssKb) = getAppPssInfo()
        val (maxKb, freeKb, allocatedKb) = getAppHeapInfo()
        val content =
            "memory info\n" +
                    ">>>>>>>>>>>>>>>> page:${page.componentName.shortClassName}\n" +
                    "[device Ram] availMem:${availMemKb}k totalMem:${(totalMemKb / (1024f * 1024f)).roundToInt()}g threshold:${lowMemThresholdKb}k lowMemory:${isLowMemory}\n" +
                    "[app pss] totalPss:${totalPssKb}k dalvikPss:${dalvikPssKb}k nativePss:${nativePssKb}k otherPss:${otherPssKb}k\n" +
                    "[app heap] freeMemory:${freeKb}k allocated:${allocatedKb}k maxMemory:${maxKb/1024}m memoryClass:${am?.memoryClass}m largeMemoryClass${am?.largeMemoryClass}m"
        Log.d(TAG_APM_MONITOR, content.toString())
    }
}