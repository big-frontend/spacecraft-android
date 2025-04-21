package com.electrolytej.main.router

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
/**
 *           Intent intent = new Intent("com.electrolytej.spacecraft.set_wallpaper");
intent.setComponent(new ComponentName(getPackageName(), "com.electrolytej.main.router.SetWallpaper"));
intent.putExtra("imei","2378478394390043");
sendBroadcast(intent);

//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.electrolytej.spacecraft.set_wallpaper");
//        registerReceiver(new SetWallpaper(), filter);
adb shell am broadcast -a com.electrolytej.spacecraft.set_wallpaperr -n com.electrolytej.spacecraft.debug/com.electrolytej.main.router.SetWallpaper --es "imei" "23435"
 */
class SetWallpaperReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Wallpaper.addTextWatermark( intent?.getStringExtra("imei") ?: "NA")
    }
}
