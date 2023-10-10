@file:JvmName("MemoryUtil")

package com.jamesfchen.vi.util

import android.app.ActivityManager
import android.content.pm.ApplicationInfo
import android.os.Debug
import android.os.Process
import androidx.core.content.ContextCompat
import com.jamesfchen.vi.Perf
import com.jamesfchen.vi.util.ConvertUtil.kbToString
import java.util.regex.Pattern


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
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
    val am: ActivityManager? by lazy {
        ContextCompat.getSystemService(Perf.app, ActivityManager::class.java)
    }

    //
//    //android4.4版本以下都被认为低ram设备
    val isLowRamDevice = am?.isLowRamDevice
    val hasLargeHeap = Perf.app.applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP != 0

    //memoryClass为普通app的heap单位为m
    fun availableHeap() = if (hasLargeHeap) am?.largeMemoryClass else am?.memoryClass

    data class HeapInfo(val maxKb: Long, val freeKb: Long, val allocatedDalvikHeapKb: Long,val allocatedNativeHeapKb: Long,val vmSize:Long)

    //App虚拟机堆内存
    fun getAppHeapInfo(): HeapInfo {
        val art = Runtime.getRuntime()
        //没有开启large heap时 maxMemory:384m ,开启large heap时 maxMemory:512m,app使用了大顶堆
        val maxMemKb = art.maxMemory() / 1024
        val freeMemKb = art.freeMemory() / 1024
        val allocatedDalvikHeapKb = (art.totalMemory() - art.freeMemory()) / 1024
        val allocatedNativeHeapKb = Debug.getNativeHeapAllocatedSize() / 1024 //in KB
        val vmSize = getVmSize()
        return HeapInfo(maxMemKb, freeMemKb, allocatedDalvikHeapKb,allocatedNativeHeapKb,vmSize)
    }

    private fun getVmSize(): Long {
        val status = String.format("/proc/%s/status", Process.myPid())
        try {
            val content: String = DeviceUtil.getStringFromFile(status).trim { it <= ' ' }
            val args = content.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (str in args) {
                if (str.startsWith("VmSize")) {
                    val p = Pattern.compile("\\d+")
                    val matcher = p.matcher(str)
                    if (matcher.find()) {
                        return matcher.group().toLong()
                    }
                }
            }
            if (args.size > 12) {
                val p = Pattern.compile("\\d+")
                val matcher = p.matcher(args[12])
                if (matcher.find()) {
                    return matcher.group().toLong()
                }
            }
        } catch (e: Exception) {
            return -1
        }
        return -1
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
    @JvmStatic
    fun getRamInfo(): RamInfo {
        val m = ActivityManager.MemoryInfo()
        am?.getMemoryInfo(m)
        return RamInfo(m.availMem / 1024, m.lowMemory, m.threshold / 1024, m.totalMem / 1024)
    }
    @JvmStatic
    fun getAppMemoryInfo():String {
        val (availMemKb, isLowMemory, lowMemThresholdKb, totalMemKb) = getRamInfo()
        val (totalPssKb,
            dalvikPssKb,
            nativePssKb,
            otherPssKb) = getAppPssInfo()
        val (maxKb, freeKb, allocatedDalvikHeapKb,allocatedNativeHeapKb,vmSize) = getAppHeapInfo()
        val content =
            "memory info " +
                    "[device Ram] availMem:${kbToString(availMemKb)} totalMem:${kbToString(totalMemKb)} threshold:${kbToString(lowMemThresholdKb)} lowMemory:${isLowMemory}\n" +
                    "[app pss] totalPss:${kbToString(totalPssKb.toLong())} dalvikPss:${kbToString(dalvikPssKb.toLong())} nativePss:${kbToString(nativePssKb.toLong())} otherPss:${kbToString(otherPssKb.toLong())}\n" +
                    "[app heap] freeMemory:${kbToString(freeKb)} allocatedDalvikHeap:${kbToString(allocatedDalvikHeapKb)} allocatedDalvikHeap:${kbToString(allocatedNativeHeapKb)} maxMemory:${kbToString(maxKb)} vmSize:${kbToString(vmSize)} " +
                    "memoryClass:${kbToString(am?.memoryClass!!.toLong() * 1024)} largeMemoryClass:${
                        kbToString(
                            am?.largeMemoryClass!!.toLong() * 1024)
                    }"
        return content
    }

}