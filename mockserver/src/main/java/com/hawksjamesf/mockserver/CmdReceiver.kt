package com.hawksjamesf.mockserver

import android.content.*
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.hawksjamesf.common.util.Util
import com.hawksjamesf.mockserver.service.MockService


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
adb shell am broadcast -a com.hawksjamesf.spacecraft.cmd_list -n com.hawksjamesf.spacecraft.debug/com.hawksjamesf.mockserver.CmdReceiver --ei "pid" 23435
adb shell am broadcast -a com.hawksjamesf.spacecraft.cmd_list -n com.hawksjamesf.spacecraft.debug/com.hawksjamesf.mockserver.CmdReceiver --ez "start_service" true
 */
class CmdReceiver : BroadcastReceiver() {
    companion object {

        private val TAG = Constants.TAG + "/CmdReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val pid = intent?.getIntExtra("pid", -1) ?: -1
        val start_service = intent?.getBooleanExtra("start_service", false) ?: false
        if (pid != -1) {
            Log.d(TAG, "pid:$pid")
            Process.killProcess(pid)

        } else if (start_service) {
            val topActivity = ActivityUtils.getTopActivity()
            Toast.makeText(topActivity, "start &  bind mock service", Toast.LENGTH_LONG).show()
            MockService.bindAndStartService(topActivity!!, object : ServiceConnection {
                override fun onServiceDisconnected(name: ComponentName?) {

                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                }
            })

        }
    }
}
