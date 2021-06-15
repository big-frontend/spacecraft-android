package com.jamesfchen.myhome;

import android.app.WallpaperManager
import android.graphics.Color
import android.os.Bundle;
import android.util.Log

import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.Utils

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/19/2020  Thu
 * adb shell am start -n com.hawksjamesf.spacecraft.debug/com.hawksjamesf.myhome.BlankActivity --es "imei" "23435"
 */
public class BlankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val wallpaperIntent = Intent("com.hawksjamesf.spacecraft.set_wallpaper");
//        wallpaperIntent.setComponent(ComponentName(packageName, "com.hawksjamesf.myhome.SetWallpaper"));
//        val imei:String? = intent.getStringExtra("imei")
//        if (imei?.isNotEmpty() == true) {
//            wallpaperIntent.putExtra("imei", imei)
//        }
//        sendBroadcast(wallpaperIntent);
        val app = Utils.getApp()
        val drawable = app?.resources?.getDrawable(R.drawable.cjf)
        var myBitmap = ImageUtils.drawable2Bitmap(drawable)
        Log.d("cjf", "drawable：${drawable?.intrinsicWidth} ${drawable?.intrinsicHeight} bitmap:${myBitmap?.width}  ${myBitmap?.height}")
        var wallpaperManager = WallpaperManager.getInstance(app)
        val stringExtra = intent?.getStringExtra("imei") ?: "NA"
        val addTextWatermark = ImageUtils.addTextWatermark(myBitmap, stringExtra, ConvertUtils.dp2px(60f), Color.BLUE, 50f, 1000f)
        wallpaperManager?.setBitmap(addTextWatermark)
        finish()
    }
}
