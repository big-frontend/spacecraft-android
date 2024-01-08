@file:JvmName("DimenUtil")

package jamesfchen.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.WindowManager
import androidx.annotation.Dimension

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/01/2021  Tue
 */
fun Float.dp2px(cxt: Context): Int {
    val scale = cxt.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}


fun Float.px2dp(cxt: Context): Int {
    val scale = cxt.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
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

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE
)
@Dimension(unit = Dimension.SP)
annotation class SP

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE
)
@Dimension(unit = Dimension.DP)
annotation class DP