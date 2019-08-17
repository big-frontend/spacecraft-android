package com.hawksjamesf.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class TraceTime
//@java.lang.annotation.Retention(RetentionPolicy.CLASS)
//annotation class AddTrace(val name: String, val enabled: Boolean = true)
