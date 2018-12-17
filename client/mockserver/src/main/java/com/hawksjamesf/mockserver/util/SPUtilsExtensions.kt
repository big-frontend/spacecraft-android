package com.hawksjamesf.mockserver.util

import android.support.annotation.IntDef
import com.blankj.utilcode.util.SPUtils

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/27/2018  Thu
 */
const val USER = 1
const val GLOBAL = 2
@IntDef(value = [USER, GLOBAL])
@Retention(AnnotationRetention.SOURCE)
private annotation class Level

fun <T:SPUtils> T.put(@Level level: Int, key: String, value: Boolean) {
    when (level) {
        USER -> {

        }

        GLOBAL -> {

        }

    }
}