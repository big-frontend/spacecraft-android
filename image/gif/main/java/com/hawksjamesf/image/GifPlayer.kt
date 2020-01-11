package com.hawksjamesf.image

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jan/07/2020  Tue
 */
class GifPlayer : BitmapListener {
    companion object {
        const val MSG_START = 0
        const val MSG_PAUSE = 1
        const val MSG_STOP = 2

        init {
            System.loadLibrary("gif_jni")
        }
    }

    var mEventHandler: EventHandler
    private var mImageView: ImageView? = null

    init {
        mEventHandler = when {
            Looper.myLooper() != null -> {
                EventHandler(this, Looper.myLooper()!!)
            }
            Looper.getMainLooper() != null -> {
                EventHandler(this, Looper.getMainLooper())
            }
            else -> {
                EventHandler(this, Looper.getMainLooper())
            }
        }
    }


    external fun setDataSource(assetName: String, manager: AssetManager, bitmap: Bitmap?)
    external fun setDataSource(uriPath: String, bitmap: Bitmap?)
    external fun getGifWidth(): Int
    external fun getGifHeight(): Int
    external fun start()

//    external fun setBitmap(bitmap: Bitmap?)
//    fun start() {
//        mEventHandler.sendEmptyMessage(MSG_START)
//    }

    fun pause() {
        mEventHandler.sendEmptyMessage(MSG_PAUSE)
    }

    fun stop() {
        mEventHandler.removeMessages(MSG_START)
        mEventHandler.removeMessages(MSG_PAUSE)
    }


    fun createAndBind(context: Context, ivGif: ImageView, assetName: String, assetManager: AssetManager): GifPlayer {
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        setDataSource(assetName, assetManager, bitmap)//setSource 之后才能拿到width、height
        bindImageView(ivGif)
        return this
    }

    //来自网络的地址，来自sdcard的地址
    fun createAndBind(context: Context, ivGif: ImageView, uri: String): GifPlayer {
        val bitmap = Bitmap.createBitmap(getGifWidth(), getGifHeight(), Bitmap.Config.ARGB_8888)
        setDataSource(uri, bitmap)//setSource 之后才能拿到width、height
        bindImageView(ivGif)
        return this
    }

    fun bindImageView(imageView: ImageView) {
//        setBitmap(bitmap)
        mImageView = imageView
    }


    inner class EventHandler : Handler {
        private var mPlayer: GifPlayer

        constructor(player: GifPlayer, looper: Looper) : super(looper) {
            mPlayer = player
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_START -> {
//                    setImageBitmap(updateBitmap())
                    sendEmptyMessage(MSG_START)
                }
                MSG_PAUSE -> {
                }
                MSG_STOP -> {
                }

            }
        }
    }

    override fun onBitmapAvailable(bitmap: Bitmap?, width: Int, height: Int) {
        Log.d("hawks", "onBitmapAvailable:$bitmap--$width/$height")

    }

    override fun onBitmapSizeChanged(bitmap: Bitmap?, width: Int, height: Int) {
        Log.d("hawks", "onBitmapSizeChanged:$bitmap--$width/$height")
    }

    override fun onBitmapDestroyed(bitmap: Bitmap?): Boolean {
        Log.d("hawks", "onBitmapDestroyed:$bitmap")
        return true
    }

    override fun onBitmapUpdated(bitmap: Bitmap?) {
        mImageView?.setImageBitmap(bitmap)
        Log.d("hawks", "onBitmapUpdated:$bitmap")
    }
}