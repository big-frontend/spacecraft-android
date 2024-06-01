package com.electrolytej.main.router;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/**
 *
 * adb shell am start -n com.electrolytej.spacecraft.debug/com.electrolytej.main.router.BlankActivity --es "imei" "23435"
 */
public class BlankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val wallpaperIntent = Intent("com.electrolytej.spacecraft.set_wallpaper");
//        wallpaperIntent.setComponent(ComponentName(packageName, "com.electrolytej.main.router.SetWallpaper"));
//        val imei:String? = intent.getStringExtra("imei")
//        if (imei?.isNotEmpty() == true) {
//            wallpaperIntent.putExtra("imei", imei)
//        }
//        sendBroadcast(wallpaperIntent);
        Wallpaper.addTextWatermark( intent?.getStringExtra("imei") ?: "NA")
        finish()
    }
}
