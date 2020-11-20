package com.hawksjamesf.myhome

import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.util.Log
import com.blankj.utilcode.util.*
import java.io.File


/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/19/2020  Thu
 *
 *           Intent intent = new Intent("com.hawksjamesf.spacecraft.set_wallpaper");
intent.setComponent(new ComponentName(getPackageName(), "com.hawksjamesf.myhome.SetWallpaper"));
intent.putExtra("imei","2378478394390043");
sendBroadcast(intent);

//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.hawksjamesf.spacecraft.set_wallpaper");
//        registerReceiver(new SetWallpaper(), filter);
adb shell am broadcast -a com.hawksjamesf.spacecraft.set_wallpaperr -n com.hawksjamesf.spacecraft.debug/com.hawksjamesf.myhome.SetWallpaper --es "imei" "23435"
 */
class SetWallpaper : BroadcastReceiver() {
    var wallpaperManager: WallpaperManager? = null
    var myBitmap:Bitmap?=null
    init {
        val app = Utils.getApp()
        val drawable = app?.resources?.getDrawable(R.drawable.cjf)
        myBitmap = ImageUtils.drawable2Bitmap(drawable)
        Log.d("cjf", "drawable：${drawable?.intrinsicWidth} ${drawable?.intrinsicHeight} bitmap:${myBitmap?.width}  ${myBitmap?.height}")
        wallpaperManager = WallpaperManager.getInstance(app)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val stringExtra = intent?.getStringExtra("imei") ?: "NA"
        val addTextWatermark = ImageUtils.addTextWatermark(myBitmap, stringExtra, ConvertUtils.dp2px(60f), Color.BLUE, 50f, 1000f)
        wallpaperManager?.setBitmap(addTextWatermark)
    }
}
