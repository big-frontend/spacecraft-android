package com.hawksjamesf.common.util

import androidx.annotation.Dimension

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/14/2019  Sat
 */
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