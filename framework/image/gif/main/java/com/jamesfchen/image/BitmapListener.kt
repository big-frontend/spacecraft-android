package com.jamesfchen.image

import android.graphics.Bitmap

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jan/07/2020  Tue
 */
interface BitmapListener {
    fun onBitmapAvailable(bitmap: Bitmap?, width: Int, height: Int)

    fun onBitmapSizeChanged(bitmap: Bitmap?, width: Int, height: Int)

    fun onBitmapDestroyed(bitmap: Bitmap?): Boolean
    fun onBitmapUpdated(bitmap: Bitmap?)

}
typealias onBitmapUpdated = (bitmap: Bitmap) -> Unit