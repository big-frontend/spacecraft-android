@file:JvmName("DimenUtil")
package com.electrolytej.util

import androidx.annotation.Dimension

fun Float.dp2px(): Int {
    val scale = Util.getApp().baseContext.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}


fun Float.px2dp(): Int {
    val scale = Util.getApp().baseContext.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FIELD,AnnotationTarget.LOCAL_VARIABLE)
@Dimension(unit = Dimension.SP)
annotation class SP

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FIELD,AnnotationTarget.LOCAL_VARIABLE)
@Dimension(unit = Dimension.DP)
annotation class DP