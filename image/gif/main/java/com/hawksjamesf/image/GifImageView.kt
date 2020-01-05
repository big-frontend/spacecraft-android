package com.hawksjamesf.image

import android.content.Context
import android.content.res.AssetManager
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import java.net.URI

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

    //来自网络的地址，来自sdcard的地址
    fun setSource(uri: URI) {
        setSource1(uri.toString())
    }
    external fun setSource(assetName: String, manager: AssetManager)
    external fun setSource1(uriPath: String)

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