package com.hawksjamesf.map

import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.blankj.utilcode.util.NetworkUtils
import com.hawksjamesf.common.util.DeviceUtil
import com.hawksjamesf.map.model.AppCellInfo
import com.hawksjamesf.map.model.AppLocation
import com.hawksjamesf.map.model.LBS
import com.hawksjamesf.map.model.LBSViewModel
import com.hawksjamesf.map.service.LbsIntentServices
import com.hawksjamesf.map.service.LbsJobIntentService
import com.hawksjamesf.map.service.LbsJobService
import com.hawksjamesf.map.service.LbsServiceConnection
import kotlinx.android.synthetic.main.activity_location.*
import java.io.File

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/27/2020  Mon
 */
class LocationActivity : PermissionsActivity() {
    private val viewModel by viewModels<LBSViewModel>()
    private val connection = LbsServiceConnection()
    lateinit var lbsFile: File
    var ibsListenerStub: ILbsListener.Stub = object : ILbsListener.Stub() {
        val pid: Int
            get() = android.os.Process.myPid()

        @Throws(RemoteException::class)
        override fun onLocationChanged(appLocation: AppLocation?, appCellInfo: AppCellInfo?, count: Long) {
//            this@LocationActivity.runOnUiThread(Runnable { bt_cellInfos.text = "统计次数：$count" })
            Log.d(TAG, "onLocationChanged:index:" + count + "\n" +
                    "${appLocation?.lat},${appLocation?.lon}\n" +
                    "${appCellInfo?.lac},${appCellInfo?.cid}")
            bt_cellInfos.text = "统计次数：$count"
            viewModel.insert(appCellInfo, appLocation)
            ReportApi.reportLocation(appLocation,appCellInfo,count, auth)
//            FileIOUtils.write2File(lbsFile, location, cellInfoList, count, auth)
        }

        @Throws(RemoteException::class)
        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
//            Log.d(TAG, "onStatusChanged:$s  $i $bundle")
        }

        @Throws(RemoteException::class)
        override fun onProviderEnabled(s: String) {
            Log.d(TAG, "onProviderEnabled:$s")
        }

        @Throws(RemoteException::class)
        override fun onProviderDisabled(s: String) {
            Log.d(TAG, "onProviderDisabled:$s")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val ipAddressByWifi = NetworkUtils.getIpAddressByWifi()
        val ipAddressipv4 = NetworkUtils.getIPAddress(true)
        val ipAddressipv6 = NetworkUtils.getIPAddress(false)
        Log.d(TAG, "onCreate: " + ipAddressByWifi + "  " + ipAddressipv4 + " " + ipAddressipv6)
        val macAddressByNetworkInterface = DeviceUtil.getMacAddressByNetworkInterface()
        val macAddressByInetAddress = DeviceUtil.getMacAddressByInetAddress()
        val macAddressByWifiInfo = DeviceUtil.getMacAddressByWifiInfo()
        val getMacAddressByFile = DeviceUtil.getMacAddressByFile()
        Log.d(TAG, "onCreate: 网卡：" + macAddressByNetworkInterface + "\n网络地址:" + macAddressByInetAddress + "\nwifi:" + macAddressByWifiInfo + "\nfile:" + getMacAddressByFile)

        val adapter = LbsAdapter()
        rv_cellinfos.adapter = adapter
        val observer = Observer(adapter::submitList)
        viewModel.allLbsDatas.observe(this, Observer<PagedList<LBS>> { p ->
            adapter.submitList(p)
            if (p.size > 2) {
                rv_cellinfos.smoothScrollToPosition(p.size - 1)
            }
        })
        viewModel.clearAll()
        srf_cellinfos.setOnRefreshListener {
            srf_cellinfos.isRefreshing = false
        }
//        val lbsDir = File(Environment.getExternalStorageDirectory().absoluteFile, "lbsPath")
//        if (!lbsDir.exists()) {
//            lbsDir.mkdir()
//        }
//        this.lbsFile = File(lbsDir, "lbsPath_" + System.currentTimeMillis() + ".json")
        connection.listener = ibsListenerStub

    }

    override fun onResult() {
        super.onResult()
        //如果您的应用在后台运行，它每小时只能接收几次位置信息更新
        LbsIntentServices.startAndBindService(this, connection)
        LbsJobService.startService(this)
        LbsJobIntentService.startService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        LbsIntentServices.stopAndUnbindService(this, connection)
    }

}