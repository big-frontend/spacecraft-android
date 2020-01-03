package com.hawksjamesf.image

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import java.io.InputStream
import java.net.URL

class GifImageView : AppCompatImageView {
    companion object {
        const val MSG_START = 0
        const val MSG_PAUSE = 1
        const val MSG_STOP = 2

        init {
            System.loadLibrary("gif")
        }
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val handler = UIHandler()

    init {

    }

    fun setSource(assetFd:AssetFileDescriptor) {}
    fun setSource(inputSteam:InputStream) {}
    fun setSource(url:URL) {}
//    fun setSource(assetName:String) {}

    external fun setSource(assetName: String):Unit
    fun start() {
        handler.sendEmptyMessage(MSG_START)

    }

    fun pause() {
        handler.sendEmptyMessage(MSG_PAUSE)
    }

    fun stop() {
        handler.removeMessages(MSG_START)
        handler.removeMessages(MSG_PAUSE)
    }
     class UIHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_START -> {
                    sendEmptyMessage(MSG_START)
                }
                MSG_PAUSE -> {
                }
                MSG_STOP -> {
                }

            }
        }
    }

}