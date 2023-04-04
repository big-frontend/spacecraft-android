@file:JvmName("DimenUtil")
package com.jamesfchen.common.util

import androidx.annotation.Dimension

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/01/2021  Tue
 */
fun Float.dp2px(): Int {
    val scale = Util.getApp().baseContext.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}


fun Float.px2dp(): Int {
    val scale = Util.getApp().baseContext.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FIELD,AnnotationTarget.LOCAL_VARIABLE)
@Dimension(unit = Dimension.SP)
annotation class SP

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FIELD,AnnotationTarget.LOCAL_VARIABLE)
@Dimension(unit = Dimension.DP)
annotation class DP