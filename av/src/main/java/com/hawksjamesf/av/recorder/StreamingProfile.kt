@file:JvmName("StreamingProfile")

package com.hawksjamesf.av.recorder

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


private val mAspectRatio = getScreenHeight() / (getScreenWidth() * 1.0)

private fun getScreenWidth(): Int {
    val wm = Utils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return -1
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return point.x
}

private fun getScreenHeight(): Int {
    val wm = Utils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return -1
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return point.y
}

interface QUALITY {
    val bitRate: Int
    val frameRate: Int

}

class Custom : QUALITY {
    //当bit rate为1_800_000时，app为后台时，i p帧出率低
    override val bitRate: Int
        //            get() = 1_800_000//Mbps
        get() = 500_000//Kbps
    override val frameRate: Int
        //            get() = 15
        get() = 30
//        override val width: Int
//            get() = 1080
//        override val height: Int
//            get() = 1920
}

interface Resolution {
    val aspectRatio: String
        get() {
            return when (mAspectRatio) {
                16 / 9.0 -> {
                    "16:9"
                }
                (4 / 3.0) -> {
                    "4:3"
                }
                (1 / 1.0) -> {
                    "1:1"
                }
                else -> {
                    ""
                }
            }
        }
    val width: Int
    val height: Int
        get() {
            return when (mAspectRatio) {
                16 / 9.0 -> {
                    9 * width / 16
                }
                (4 / 3.0) -> {
                    3 * width / 4
                }
                (1 / 1.0) -> {
                    width
                }
                else -> {
                    0
                }
            }
        }
}

object VIDEO_QUALITY_LOW1 : QUALITY {
    override val bitRate: Int = 150_000//Kbps
    override val frameRate: Int = 12//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_LOW2 : QUALITY {
    override val bitRate: Int = 264_000//Kbps
    override val frameRate: Int = 15//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_LOW3 : QUALITY {
    override val bitRate: Int = 350_000//Mbps
    override val frameRate: Int = 15//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_MEDIUM1 : QUALITY {
    override val bitRate: Int = 512_000//Mbps
    override val frameRate: Int = 30//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_MEDIUM2 : QUALITY {
    override val bitRate: Int = 800_000//Mbps
    override val frameRate: Int = 30//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_MEDIUM3 : QUALITY {
    override val bitRate: Int = 1000_000//Mbps
    override val frameRate: Int = 30//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_HIGH1 : QUALITY {
    override val bitRate: Int = 1200_000//Mbps
    override val frameRate: Int = 30//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_HIGH2 : QUALITY {
    override val bitRate: Int = 1500_000//Mbps
    override val frameRate: Int = 30//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_QUALITY_HIGH3 : QUALITY {
    override val bitRate: Int = 2000_000//Mbps
    override val frameRate: Int = 30//fps
//            override val width: Int = 0
//            override val height: Int = 0
}

object VIDEO_ENCODING_WIDTH_240 : Resolution {
    override val width: Int
        get() = 240
}

object VIDEO_ENCODING_WIDTH_480 : Resolution {
    override val width: Int
        get() = 480
}

object VIDEO_ENCODING_HEIGHT_544 : Resolution {
    override val width: Int
        get() = 544
}

object VIDEO_ENCODING_HEIGHT_720 : Resolution {
    override val width: Int
        get() = 720
}

object VIDEO_ENCODING_HEIGHT_1080 : Resolution {
    override val width: Int
        get() = 1080
}

