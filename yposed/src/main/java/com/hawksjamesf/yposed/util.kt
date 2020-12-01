@file:JvmName("Util")

package com.hawksjamesf.yposed

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils
import androidx.annotation.Keep
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/27/2020  Fri
 */
@Keep
fun sha1ToHexString(cert: ByteArray): String? {
    try {
        val md = MessageDigest.getInstance("SHA1")
        val publicKey = md.digest(cert)
        val hexString = StringBuffer()
        for (i in publicKey.indices) {
            val appendString = Integer.toHexString(0xFF and publicKey[i].toInt())
                    .toUpperCase(Locale.US)
            if (appendString.length == 1) hexString.append("0")
            hexString.append(appendString)
            hexString.append(":")
        }
        val result = hexString.toString()
        return result.substring(0, result.length - 1)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return null
}
fun isMainProcess(context: Context): Boolean {
    val processName = getProcessName(context, Process.myPid())
    val packageName = context.applicationContext.packageName
    if (TextUtils.isEmpty(processName) || TextUtils.isEmpty(packageName)) {
        return false
    }
    return packageName == processName
}

private fun getProcessName(context: Context, i: Int): String? {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    if (activityManager == null || activityManager.runningAppProcesses == null) {
        return null
    }
    val runningAppProcesses: List<ActivityManager.RunningAppProcessInfo> = activityManager.runningAppProcesses
    for (next in runningAppProcesses) {
//        Log.d("cjf","process name:${next.processName}")
        if (next.pid == i) {
            return next.processName
        }
    }
    return null
}
fun getSysProp(str: String): String {
    return if (TextUtils.isEmpty(str)) {
        ""
    } else try {
        val cls = Class.forName("android.os.SystemProperties")
        cls.getDeclaredMethod("get", String::class.java).invoke(cls, str) as String
    } catch (th: Throwable) {
        ""
    }
}