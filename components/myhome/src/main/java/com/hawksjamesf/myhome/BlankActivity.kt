package com.hawksjamesf.myhome;

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle;
import android.util.Log

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ImageUtils
import java.io.File

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
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        val intent =  Intent("com.hawksjamesf.spacecraft.set_wallpaper");
        intent.setComponent(ComponentName(packageName, "com.hawksjamesf.myhome.SetWallpaper"));
        intent.putExtra("imei","869747042050258");
        sendBroadcast(intent);
        finish()
    }
}
