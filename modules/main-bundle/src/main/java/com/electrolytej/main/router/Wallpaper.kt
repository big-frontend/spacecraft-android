package com.electrolytej.main.router

import android.app.WallpaperManager
import android.graphics.Color
import android.util.Log
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.Utils
import com.electrolytej.main.R

/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: Jun/01/2024  Sat
 */
object Wallpaper {
    fun addTextWatermark(text: String) {
        val app = Utils.getApp()
        val drawable = app?.resources?.getDrawable(R.drawable.wallpaper)
        var myBitmap = ImageUtils.drawable2Bitmap(drawable)
        Log.d(
            "Wallpaper",
            "drawable：${drawable?.intrinsicWidth} ${drawable?.intrinsicHeight} bitmap:${myBitmap?.width}  ${myBitmap?.height}"
        )
        var wallpaperManager = WallpaperManager.getInstance(app)
        val addTextWatermark = ImageUtils.addTextWatermark(
            myBitmap, text, ConvertUtils.dp2px(60f), Color.BLUE, 50f, 1000f
        )
        wallpaperManager?.setBitmap(addTextWatermark)
    }

}