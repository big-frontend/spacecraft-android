package com.electrolytej.tool.util

import androidx.annotation.IntDef
import com.blankj.utilcode.util.SPUtils


const val USER = 1
const val GLOBAL = 2

@IntDef(value = [USER, GLOBAL])
@Retention(AnnotationRetention.SOURCE)
private annotation class Level

fun <T : SPUtils> T.put(@Level level: Int, key: String, value: Boolean) {
    when (level) {
        USER -> {

        }

        GLOBAL -> {

        }

    }
}