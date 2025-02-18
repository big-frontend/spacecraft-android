@file:JvmName("ResourceUtil")

package com.electrolytej.util

import androidx.annotation.RawRes

private const val RAW_PATH: String = "android.resource://%s/%s"
private const val ASSETS_PATH: String = "file:///android_asset/%s"

fun getRawPath(packageName: String, @RawRes resid: Int): String {
    return String.format(RAW_PATH, packageName, resid)
}

fun getAssetsPath(fileName: String): String {
    return String.format(ASSETS_PATH, fileName)
}
