@file:JvmName("DimenUtil")

package com.electrolytej.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.WindowManager
import androidx.annotation.Dimension
import androidx.annotation.Px

@Dimension(unit = Dimension.SP)
annotation class SP

@Dimension(unit = Dimension.DP)
annotation class DP

@Px
fun Float.dp2px(): Int {
    val scale = Util.getApp().baseContext.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

@DP
fun Float.px2dp(): Int {
    val scale = Util.getApp().baseContext.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

@Px
fun Float.sp2px(): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (this * fontScale + 0.5f).toInt()
}

@SP
fun Float.px2sp(): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (this / fontScale + 0.5f).toInt()
}

fun getScreenDensityDpi(): Int {
    return Resources.getSystem().displayMetrics.densityDpi
}

fun getScreenWidth(cxt: Context): Int {
    val wm = cxt.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return point.x
}

fun getScreenHeight(cxt: Context): Int {
    val wm = cxt.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return point.y
}


@get:Px
val Float.dp
    get() = dp2px()

@get:Px
val Int.dp
    get() = this.toFloat().dp

@get:Px
val Float.sp
    get() = sp2px()

@get:Px
val Int.sp
    get() = this.toFloat().sp

