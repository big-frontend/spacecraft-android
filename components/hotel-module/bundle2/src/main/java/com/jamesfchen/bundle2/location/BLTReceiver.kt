package com.jamesfchen.bundle2.location

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context

class BLTReceiver {
    companion object {

        @JvmStatic
        fun getInstance(context: Context) {

        }

    }

    lateinit var bltManager: BluetoothManager
    fun start(context: Context) {
        bltManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bltManager.adapter.startLeScan(mLesanCall)

    }

    private val mLesanCall = object : BluetoothAdapter.LeScanCallback {
        override fun onLeScan(device: BluetoothDevice?, rssi: Int, scanRecord: ByteArray?) {

        }
    }
}