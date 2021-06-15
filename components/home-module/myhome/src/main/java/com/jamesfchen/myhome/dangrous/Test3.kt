package com.jamesfchen.myhome.dangrous

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.graphics.toColorInt

class Test3 {
    @SuppressLint("LogNotTimber")
    fun test() {
        "adfasf".toInt()
//        "adfasf".toIntOrNull()
        "adfasf".toLong()
        "adfasf".toFloat()
//        "adfasf".toDouble()
        "#123424".toColorInt()
        Log.e("cjf","fasdf")
    }
    val s = "lint"
    fun lint() { }

}