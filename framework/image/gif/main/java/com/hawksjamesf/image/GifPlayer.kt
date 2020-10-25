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
class GifPlayer : InnerBitmapListener {
    companion object {
        const val MSG_START = 0
        const val MSG_PAUSE = 1
        const val MSG_STOP = 2

        init {
            System.loadLibrary("gif_jni")
        }

        fun createAndBind(context: Context, ivGif: ImageView, assetName: String, assetManager: AssetManager): GifPlayer {
            val player = GifPlayer()
            player.nativePlayerAddress = player.setDataSource(assetName, assetManager)//setSource 之后才能拿到width、height
            val bitmap = Bitmap.createBitmap(player.getGifWidth(player.nativePlayerAddress), player.getGifHeight(player.nativePlayerAddress), Bitmap.Config.ARGB_8888)
            player.bindImageView(ivGif, bitmap)
            return player
        }

        //来自网络的地址，来自sdcard的地址
        fun createAndBind(context: Context, ivGif: ImageView, uri: String): GifPlayer {
            val player = GifPlayer()
            player.nativePlayerAddress = player.setDataSource(uri)//setSource 之后才能拿到width、height
            player.bindImageView(ivGif)
            return player
        }
    }

    var mEventHandler: EventHandler
    private lateinit var mImageView: ImageView
    private lateinit var mBitmap: Bitmap
    private var nativePlayerAddress: Long = 0
    private var mBitmapListener: BitmapListener? = null
    fun setBitmapListener(listener: BitmapListener) {
        mBitmapListener = listener
    }

    private var startblock: onBitmapUpdated? = null

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

    external fun setDataSource(assetName: String, manager: AssetManager): Long
    external fun setDataSource(uriPath: String): Long
    fun bindImageView(imageView: ImageView) {
        val bitmap = Bitmap.createBitmap(getGifWidth(nativePlayerAddress), getGifHeight(nativePlayerAddress), Bitmap.Config.ARGB_8888)
                ?: throw  IllegalArgumentException("bitmap must be not null")
        bindImageView(imageView, bitmap)
    }

    fun bindImageView(imageView: ImageView, bitmap: Bitmap) {
        mImageView = imageView
        mBitmap = bitmap
        imageView.setImageBitmap(bitmap)
        bindBitmap(nativePlayerAddress, bitmap)
    }

    private external fun bindBitmap(nativePlayerAddress: Long, bitmap: Bitmap?)
    private external fun getGifWidth(nativePlayerAddress: Long): Int
    private external fun getGifHeight(nativePlayerAddress: Long): Int
    private external fun start(nativePlayerAddress: Long)
    fun start(block: onBitmapUpdated? = null) {
        start(nativePlayerAddress)
        startblock = block
    }

    fun pause() {
        mEventHandler.sendEmptyMessage(MSG_PAUSE)
    }

    fun stop() {
        mEventHandler.removeMessages(MSG_START)
        mEventHandler.removeMessages(MSG_PAUSE)
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


    override fun onBitmapAvailable(width: Int, height: Int) {
        Log.d("hawks", "onBitmapAvailable:${mBitmap.byteCount}--$width/$height")
    }

    override fun onBitmapSizeChanged(width: Int, height: Int) {
        Log.d("hawks", "onBitmapSizeChanged:${mBitmap.byteCount}--$width/$height")
    }

    override fun onBitmapDestroyed(): Boolean {
        Log.d("hawks", "onBitmapDestroyed:${mBitmap.byteCount}")
        return true
    }

    override fun onBitmapUpdated() {
//        mImageView?.setImageBitmap(mBitmap)
        Log.d("hawks", "onBitmapUpdated:${mBitmap.byteCount}")
    }
}