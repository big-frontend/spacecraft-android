package com.hawksjamesf.map.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.hawksjamesf.map.ILbsApi
import com.hawksjamesf.map.ILbsListener

class LbsServiceConnection : ServiceConnection {
    lateinit var iLbsApi: ILbsApi
    var listener: ILbsListener.Stub? = null
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