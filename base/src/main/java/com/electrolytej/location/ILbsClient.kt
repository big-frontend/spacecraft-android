package com.electrolytej.location

import android.app.Activity
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.electrolytej.location.ILbsApi
import com.electrolytej.location.ILbsListener

class ILbsClient(
    val activity: Activity
) {
    val connection = LbsServiceConnection()
    lateinit var iLbsApi: ILbsApi
    var listener: ILbsListener.Stub? = null

    open class Listener : ILbsListener.Stub() {
        @Throws(RemoteException::class)
        override fun onLocationChanged(
            appLocation: AppLocation?,
            appCellInfos: MutableList<AppCellInfo>?,
            count: Long
        ) {

        }


        @Throws(RemoteException::class)
        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
//            Log.d(Constants.TAG, "onStatusChanged:$s  $i $bundle")
        }

        @Throws(RemoteException::class)
        override fun onProviderEnabled(s: String) {
            Log.d(Constants.TAG, "onProviderEnabled:$s")
        }

        @Throws(RemoteException::class)
        override fun onProviderDisabled(s: String) {
            Log.d(Constants.TAG, "onProviderDisabled:$s")
        }
    }

    inner class LbsServiceConnection : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            iLbsApi = ILbsApi.Stub.asInterface(iBinder)
            try {
                iLbsApi.registerListener(listener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            try {
                iLbsApi.unregisterListener(listener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

    }



    fun startLocation() {
        //如果您的应用在后台运行，它每小时只能接收几次位置信息更新
        LbsServices.startAndBindService(activity, connection);
//        LbsJobService.startService(this);
//        LbsJobIntentService.startService(this);
    }

    fun stopLocation() {
        LbsServices.stopAndUnbindService(activity, connection)
    }
}