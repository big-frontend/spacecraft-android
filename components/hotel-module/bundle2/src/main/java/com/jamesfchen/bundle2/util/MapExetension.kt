@file:JvmName("MapUtil")

package com.jamesfchen.bundle2.util

import android.location.Geocoder
import android.os.AsyncTask
import android.util.Log
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.blankj.utilcode.util.Utils
import com.jamesfchen.bundle2.page.LBSActivity.TAG
import com.jamesfchen.bundle2.location.model.AppCellInfo
import com.jamesfchen.bundle2.location.model.AppLocation
import java.util.*

fun AMap?.addMarker(appLocation: AppLocation?, appCellInfo: AppCellInfo?) {
    if (this == null) return
    var cid: Long = 0
    var lac: Long = 0
    var lat = 0.0
    var lon = 0.0
    if (appLocation != null) {
        lat = appLocation.lat
        lon = appLocation.lon
    }
    if (appCellInfo != null) {
        cid = appCellInfo.cid
        lac = appCellInfo.lac
    }
    addMarker(lat, lon, cid, lac, "all")

}

fun AMap.addMarker(lat: Double, lon: Double, cid: Long, lac: Long, filters: String) {
    AsyncTask.THREAD_POOL_EXECUTOR.execute pool@{
        val latLng = LatLng(lat, lon)
        val geocoder = Geocoder(Utils.getApp().applicationContext, Locale.CHINESE)
        val add = geocoder.reverseGeocode2String(lat, lon)
        if ("all" != filters && !add.contains(filters)) return@pool
        Log.d(TAG, "$add  $lat,$lon $cid,$lac")
        this.addMarker(MarkerOptions().position(latLng).title(add).snippet("$latLng\ncid/lac:($cid,$lac)"))
    }
}

fun AMap?.move(lat: Double, lon: Double,lac:Long,cid:Long) {
    move(LatLng(lat, lon))
    this?.addMarker(lat, lon, cid, lac,"all")

}
fun AMap?.move(lat: Double, lon: Double) {
    move(LatLng(lat, lon))
}

fun AMap?.move(latLng: LatLng) {
    if (this == null) return
    val cameraUpdate = CameraUpdateFactory.newCameraPosition(
            //参数1---要移动到的经纬度，参数2---地图的放缩级别zoom，参数3---地图倾斜度，参数4---地图的旋转角度
            CameraPosition(latLng, 7f, 0f, 0f))
    this.moveCamera(cameraUpdate)
}