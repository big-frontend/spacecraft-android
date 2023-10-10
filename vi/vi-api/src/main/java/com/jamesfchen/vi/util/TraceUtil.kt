package com.jamesfchen.vi.util

import android.os.SystemClock
import android.os.Trace
import android.util.Log

object TraceUtil {
    private const val TRACE_ENABLED = true
    private const val LOG_ENABLED = true
    private var sections = ArrayDeque<Section>()
    @JvmStatic
    fun i(logTag:String,sectionName: String) {
        if (TRACE_ENABLED) {
            Trace.beginSection(sectionName)
        }
        if (LOG_ENABLED) {
            sections.add(Section(logTag,sectionName, SystemClock.uptimeMillis()))
        }
    }

    @JvmStatic
    fun o() {
        if (TRACE_ENABLED) {
            Trace.endSection()
        }
        if (LOG_ENABLED) {
            try {
                val section = sections.removeLast()
                val delta = SystemClock.uptimeMillis() - section.startTs
                val  a = if (delta >= 1000) "${delta/1000f}s" else "${delta}ms"
                Log.e(section.logTag, "${section.sectionName}：${a}")
            }catch (e:Exception){
                Log.e("TraceUtil", Log.getStackTraceString(e))
            }
        }
    }
    data class Section(val logTag:String,val sectionName: String, val startTs: Long)
}