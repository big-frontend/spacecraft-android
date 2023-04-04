@file:JvmName("StreamingProfile")

package com.jamesfchen.av.recorder

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.blankj.utilcode.util.Utils

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/20/2020  Tue
 */
private val screenWidth: Int by lazy {
    val wm = Utils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return@lazy point.x
}

private val screenHeight: Int by lazy {
    val wm = Utils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return@lazy point.y
}

private data class Result(val a: Int, val b: Int)

private fun cacl(a: Int, b: Int): Result {
    //    for (i in 2 until 10){}
//    for (i in IntRange(2, 9)){}
    for (i in 2..9) {
        if (a % i == 0 && b % i == 0) {
            return cacl(a / i, b / i)
        }
    }
    return Result(a, b)
}

private val mAspectRatio by lazy {
    val (a, b) = cacl(screenHeight, screenWidth)
    return@lazy "$a:$b"
}


interface QUALITY {
    val bitRate: Int
    val frameRate: Int

}

const val r_16_9 = 16 / 9.0
const val r_4_3 = 4 / 3.0
const val r_1_1 = 1 / 1.0


interface Resolution {

    val aspectRatio: String
        get() = mAspectRatio
    val width: Int
    val height: Int
        get() {
            val ration = screenHeight / (screenWidth * 1.0)
            return if (r_1_1 <= ration && ration < r_4_3) {
                width
            } else if (r_4_3 <= ration && ration < r_16_9) {
                3 * width / 4
            } else if (r_16_9 <= ration) {
                9 * width / 16
            } else {
                9 * width / 16
            }
        }
}

object VIDEO_QUALITY_LOW1 : QUALITY {
    override val bitRate: Int = 150_000//Kbps
    override val frameRate: Int = 12//fps
}

object VIDEO_QUALITY_LOW2 : QUALITY {
    override val bitRate: Int = 264_000//Kbps
    override val frameRate: Int = 15//fps
}

object VIDEO_QUALITY_LOW3 : QUALITY {
    override val bitRate: Int = 350_000//Mbps
    override val frameRate: Int = 15//fps
}

object VIDEO_QUALITY_MEDIUM1 : QUALITY {
    override val bitRate: Int = 512_000//Mbps
    override val frameRate: Int = 30//fps
}

object VIDEO_QUALITY_MEDIUM2 : QUALITY {
    override val bitRate: Int = 800_000//Mbps
    override val frameRate: Int = 30//fps
}

object VIDEO_QUALITY_MEDIUM3 : QUALITY {
    override val bitRate: Int = 1000_000//Mbps
    override val frameRate: Int = 30//fps
}

object VIDEO_QUALITY_HIGH1 : QUALITY {
    override val bitRate: Int = 1200_000//Mbps
    override val frameRate: Int = 30//fps
}

object VIDEO_QUALITY_HIGH2 : QUALITY {
    override val bitRate: Int = 1500_000//Mbps
    override val frameRate: Int = 30//fps
}

object VIDEO_QUALITY_HIGH3 : QUALITY {
    override val bitRate: Int = 2000_000//Mbps
    override val frameRate: Int = 30//fps
}

object VIDEO_ENCODING_WIDTH_240 : Resolution {
    override val width: Int
        get() = 240
}

object VIDEO_ENCODING_WIDTH_480 : Resolution {
    override val width: Int
        get() = 480
}

object VIDEO_ENCODING_WIDTH_544 : Resolution {
    override val width: Int
        get() = 544
}

object VIDEO_ENCODING_WIDTH_720 : Resolution {
    override val width: Int
        get() = 720
}

object VIDEO_ENCODING_WIDTH_1080 : Resolution {
    override val width: Int
        get() = 1080
}

object VIDEO_ENCODING_FULL_SCREEN : Resolution {
    override val height = screenHeight
    override val width = screenWidth
}
