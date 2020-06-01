package com.hawksjamesf.map

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.NetworkUtils
import com.hawksjamesf.common.util.CryptoUtil
import com.hawksjamesf.common.util.DeviceUtil

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/27/2020  Mon
 */
class LocationActivity : Activity() {
    companion object {
        const val TAG = "LocationAty-packageinfo"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val packageManager = packageManager
        val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES or PackageManager.GET_SIGNING_CERTIFICATES)
        for (i in 0 until installedPackages.size) {
            val info = installedPackages[i]
            val packagename = info.packageName
            val lastUpdateTime = info.lastUpdateTime
            val firstInstallTime = info.firstInstallTime
            val activities = info.activities
            val applicationInfo = info.applicationInfo
            val signingInfo = info.signingInfo
            val messageDigest: String = CryptoUtil.encryptMD5ToString(getRawSignature(this, info.packageName)?.get(0)?.toByteArray())
            val sb = StringBuffer()
            if (activities != null) {
                for (activityinfo in activities) {
                    sb.append(activityinfo.name)
                            .append('\t')
                }
            }
            val sb2 = StringBuffer()
            val sb3 = StringBuffer()
            if (signingInfo != null) {
                for (signature in signingInfo.apkContentsSigners) {
                    sb2.append(CryptoUtil.encryptMD5ToString(signature.toByteArray()))
                            .append('\t')

                }
                for (signingCertificateHistory in signingInfo.signingCertificateHistory) {
                    sb3.append(CryptoUtil.encryptMD5ToString(signingCertificateHistory.toByteArray()))
                            .append('\t')
                }
            }

//            Log.d("LocationAty-packageinfo", "onCreate: >>> $i $packagename  $messageDigest $firstInstallTime  $lastUpdateTime \n ${sb.toString()} \n ${sb2} \n$sb3")
        }

        val ipAddressByWifi = NetworkUtils.getIpAddressByWifi()
        val ipAddressipv4 = NetworkUtils.getIPAddress(true)
        val ipAddressipv6 = NetworkUtils.getIPAddress(false)
        Log.d(TAG, "onCreate: " + ipAddressByWifi + "  " + ipAddressipv4 + " " + ipAddressipv6)
        val macAddressByNetworkInterface = DeviceUtil.getMacAddressByNetworkInterface()
        val macAddressByInetAddress = DeviceUtil.getMacAddressByInetAddress()
        val macAddressByWifiInfo = DeviceUtil.getMacAddressByWifiInfo()
        val getMacAddressByFile = DeviceUtil.getMacAddressByFile()
        Log.d(TAG, "onCreate: 网卡：" + macAddressByNetworkInterface+"\n网络地址:"+macAddressByInetAddress+"\nwifi:" +macAddressByWifiInfo +"\nfile:"+getMacAddressByFile)
    }



    fun getRawSignature(ctx: Context?, packageName: String?): Array<Signature?>? {
        if (ctx == null || packageName.isNullOrEmpty()) return null
        val packageManager = ctx.packageManager
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES or PackageManager.GET_SIGNING_CERTIFICATES)
            packageInfo.signatures
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}