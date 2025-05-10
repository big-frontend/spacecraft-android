package com.electrolytej.util

operator fun  DoubleArray.component6(): Double = get(5)
operator fun  DoubleArray.component7(): Double = get(6)
operator fun  DoubleArray.component8(): Double = get(7)
operator fun  DoubleArray.component9(): Double = get(8)

operator fun  FloatArray.component6(): Float = get(5)
operator fun  FloatArray.component7(): Float = get(6)
operator fun  FloatArray.component8(): Float = get(7)
operator fun  FloatArray.component9(): Float = get(8)

// 自定义数据类，封装 4 个值
data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)
