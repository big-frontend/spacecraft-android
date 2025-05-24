@file:JvmName("GeocoderUtil")

package com.electrolytej.ad.util

import android.location.Geocoder
import java.io.IOException

fun Geocoder?.reverseGeocode2String(lat: Double, lon: Double): String {
    if (this == null) return ""
    return try {
        val fromLocation = this.getFromLocation(lat, lon, 1)
        if (fromLocation == null || fromLocation.isEmpty()) return ""
        val address = fromLocation[0]
        val maxAddressLineIndex = address.maxAddressLineIndex
        val add: String = if (maxAddressLineIndex >= 2) {
            address.getAddressLine(0) + address.getAddressLine(1)
        } else {
            address.getAddressLine(0)
        }
        add
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }

}