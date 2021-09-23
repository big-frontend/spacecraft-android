package com.jamesfchen.image.gif

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jan/07/2020  Tue
 */
interface InnerBitmapListener {
    fun onBitmapAvailable( width: Int, height: Int)

    fun onBitmapSizeChanged(width: Int, height: Int)

    fun onBitmapDestroyed(): Boolean
    fun onBitmapUpdated()

}