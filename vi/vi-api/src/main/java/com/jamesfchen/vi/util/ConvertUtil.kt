package com.jamesfchen.vi.util
object ConvertUtil {
    fun kbToString(xKB: Long): String {
        return if (xKB >= MemoryUnit.MB) {
            "${xKB / MemoryUnit.MB}g"
        } else if (xKB >= MemoryUnit.KB) {
            "${xKB / MemoryUnit.KB}m"
        } else {
            "${xKB}k"
        }
    }

    fun msToString(xms: Long): String {
        return if (xms > TimeUnit.S) {
            "${xms / TimeUnit.S}s"
        } else {
            "${xms}k"
        }
    }
}