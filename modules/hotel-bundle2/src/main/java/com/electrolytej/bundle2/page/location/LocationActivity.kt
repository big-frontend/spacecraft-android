package com.electrolytej.bundle2.page.location

import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.blankj.utilcode.util.NetworkUtils
import com.electrolytej.bundle2.location.ILbsListener
import com.electrolytej.bundle2.databinding.ActivityLocationBinding
import com.electrolytej.bundle2.db.model.LBS
import com.electrolytej.bundle2.location.model.AppCellInfo
import com.electrolytej.bundle2.location.model.AppLocation
import com.electrolytej.bundle2.page.LBSActivity

class LocationActivity : LBSActivity() {
    lateinit var binding:ActivityLocationBinding
    private val viewModel by viewModels<LBSViewModel>()
    var ibsListenerStub: ILbsListener.Stub = object : ILbsListener.Stub() {
        val pid: Int
            get() = android.os.Process.myPid()

        @Throws(RemoteException::class)
        override fun onLocationChanged(appLocation: AppLocation?, appCellInfos: List<AppCellInfo>?, count: Long) {
//            this@LocationActivity.runOnUiThread(Runnable { bt_cellInfos.text = "统计次数：$count" })
            val s = StringBuffer()
            var appCellInfo: AppCellInfo?=null
            for (index in 0 until (appCellInfos?.size?:0)){
                s.append("${appCellInfos?.get(index)?.lac},${appCellInfos?.get(index)?.cid}  ")
                val theCell = appCellInfos?.get(index)
                if (theCell?.isRegistered == true){
                    appCellInfo =theCell
                }
            }
            Log.d(TAG, "onLocationChanged:index:" + count + "\n" + "${appLocation?.lat},${appLocation?.lon}\n" + s)
            binding.btCellInfos.text = "统计次数：$count"
            viewModel.insert(appCellInfo, appLocation)
//            ReportApi.reportLocation(appLocation,appCellInfo,count, auth)
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
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ipAddressByWifi = NetworkUtils.getIpAddressByWifi()
        val ipAddressipv4 = NetworkUtils.getIPAddress(true)
        val ipAddressipv6 = NetworkUtils.getIPAddress(false)
        Log.d(TAG, "onCreate: " + ipAddressByWifi + "  " + ipAddressipv4 + " " + ipAddressipv6)
//        val macAddressByNetworkInterface = DeviceUtils.getMacAddressByNetworkInterface()
//        val macAddressByInetAddress = DeviceUtils.getMacAddressByInetAddress()
//        val macAddressByWifiInfo = DeviceUtils.getMacAddressByWifiInfo()
//        val getMacAddressByFile = DeviceUtils.getMacAddressByFile()
//        Log.d(TAG, "onCreate: 网卡：" + macAddressByNetworkInterface + "\n网络地址:" + macAddressByInetAddress + "\nwifi:" + macAddressByWifiInfo + "\nfile:" + getMacAddressByFile)

        val adapter = LbsAdapter()
        binding.rvCellinfos.adapter = adapter
        val observer = Observer(adapter::submitList)
        viewModel.allLbsDatas.observe(this, Observer<PagedList<LBS>> { p ->
            adapter.submitList(p)
            if (p.size > 2) {
                binding.rvCellinfos.smoothScrollToPosition(p.size - 1)
            }
        })
        viewModel.clearAll()
        binding.srfCellinfos.setOnRefreshListener {
            binding.srfCellinfos.isRefreshing = false
        }
//        val lbsDir = File(Environment.getExternalStorageDirectory().absoluteFile, "lbsPath")
//        if (!lbsDir.exists()) {
//            lbsDir.mkdir()
//        }
//        this.lbsFile = File(lbsDir, "lbsPath_" + System.currentTimeMillis() + ".json")
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}