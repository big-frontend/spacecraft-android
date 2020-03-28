@file:JvmName("MemoryUtil")

package com.hawksjamesf.common.util

import android.app.ActivityManager
import android.content.pm.ApplicationInfo
import androidx.core.content.ContextCompat

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 * https://stackoverflow.com/questions/2630158/detect-application-heap-size-in-android
 */
class MemoryUtil {
    val am by lazy {
        ContextCompat.getSystemService(Util.getApp(), ActivityManager::class.java)
    }
    val hasLargeHeap = Util.getApp().applicationInfo.flags and ApplicationInfo.FLAG_LARGE_HEAP != 0
    //This method tells you how many total bytes of heap your app is allowed to use.
    val maxMemory = Runtime.getRuntime().maxMemory();

    fun availableHeap() = if (hasLargeHeap) am?.largeMemoryClass else am?.memoryClass
}