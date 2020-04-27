package com.hawksjamesf.map

import android.app.Activity
import android.content.Context
import android.net.wifi.rtt.WifiRttManager
import android.os.Build
import android.os.Bundle
import android.util.Log

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/27/2020  Mon
 */
 class LocationActivity : Activity() {
    lateinit var wifiRttManager: WifiRttManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            wifiRttManager = getSystemService(Context.WIFI_RTT_RANGING_SERVICE) as WifiRttManager
            Log.d("LocationActivity", "onCreate: " + wifiRttManager.isAvailable)
        }
    }
}