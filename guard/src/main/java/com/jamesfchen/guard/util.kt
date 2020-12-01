@file:JvmName("Util")

package com.jamesfchen.guard

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils
import android.util.Log
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
