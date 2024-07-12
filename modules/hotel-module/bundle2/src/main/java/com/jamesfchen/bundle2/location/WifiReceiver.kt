package com.jamesfchen.bundle2.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSuggestion
import android.text.format.Formatter
import android.util.Log

class WifiReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "wifi_receiver"
        lateinit var wifiManager: WifiManager
        val receiver = WifiReceiver()
        @JvmStatic
        fun registerReceiver(context: Context) {
            wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            context.registerReceiver(
                    receiver,
                    IntentFilter().apply {
                        //wifi密码验证
                        addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
                        addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
                        addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
                        addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
                        addAction(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)
                        addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
                        addAction(WifiManager.ACTION_PICK_WIFI_NETWORK)
                        addAction(WifiManager.RSSI_CHANGED_ACTION)

                    })
            wifiManager.startScan()
//            WifiNetworkSuggestion.Builder().setIsAppInteractionRequired(true).build()
        }

        @JvmStatic
        fun unregisterReceiver(context: Context) {
            context.unregisterReceiver(receiver)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        val stringBuffer = StringBuffer("action:${intent?.action}\n")

        val connectionInfo = wifiManager.connectionInfo
        val supplicantState = connectionInfo.supplicantState
        val bssid = connectionInfo.bssid
        val ssid = connectionInfo.ssid
        val rssi = connectionInfo.rssi
        val hiddenSSID = connectionInfo.hiddenSSID
        val ipAddress = connectionInfo.ipAddress
        val linkSpeed = connectionInfo.linkSpeed
        val dhcpInfo = wifiManager.dhcpInfo
        stringBuffer.append("try to associating wifi :").append(ssid).append('/').append(bssid).append('/').append(rssi).append("dBm")
                .append('\t')
                .append(supplicantState)
                .append('\n')
                .append("ip:${Formatter.formatIpAddress(connectionInfo.ipAddress)} ")
                .append("mac:${connectionInfo.macAddress} ")
                .append("dhcp ip:${Formatter.formatIpAddress(dhcpInfo.ipAddress)} ")
                .append("dhcp server ip:${Formatter.formatIpAddress(dhcpInfo.serverAddress)} ")
                .append("dhcp gateway ip:${Formatter.formatIpAddress(dhcpInfo.gateway)} ")
                .append('\n')
        when (intent?.action) {
            WifiManager.NETWORK_STATE_CHANGED_ACTION -> {
                val netInfo = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                stringBuffer.append("net state change:${netInfo}")
            }
            WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                val preState = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, -1)
                val curState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1)
                stringBuffer.append("wifi state change  extra:${preState} ${curState} ")

            }
            WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                val wifiState = wifiManager.wifiState
                val results = wifiManager.scanResults
                stringBuffer.append("scan  extra:${success}  wifiState:${wifiState} results size:${results.size}\n")
                val s = StringBuilder()
                for (result in results) {
                    s.append(result.SSID)
                            .append("/")
                            .append(result.BSSID)
                            .append("/")
                            .append(result.level)
                            .append("dBm ")
                            .append(WifiManager.calculateSignalLevel(result.level, 5))
                            .append("")
                            .append('\t')

                }
                stringBuffer.append("$s\n")
                if (success) {
                    scanSuccess()
                } else {
                    scanFailure()
                }


            }
            WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION, WifiManager.SUPPLICANT_STATE_CHANGED_ACTION -> {
                val connected = intent?.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)
                val errorCode = intent?.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1)
                val newState = intent?.getParcelableExtra<SupplicantState>(WifiManager.EXTRA_NEW_STATE)
                stringBuffer.append("supplicant extra:${connected} ${errorCode} ${newState}")
            }

            WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION -> {
                val suggestion = intent.getParcelableExtra<WifiNetworkSuggestion>(WifiManager.EXTRA_NETWORK_SUGGESTION)
                stringBuffer.append("suggestion ")
            }

            WifiManager.ACTION_PICK_WIFI_NETWORK -> {
                stringBuffer.append("pick wifi ")
            }
            WifiManager.RSSI_CHANGED_ACTION -> {
                val newRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -1)
                stringBuffer.append("rssi change : ${newRssi}")
            }
        }

        Log.d(TAG, stringBuffer.toString())
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        val wifiState = wifiManager.wifiState
    }

    private fun scanFailure() {
    }
}