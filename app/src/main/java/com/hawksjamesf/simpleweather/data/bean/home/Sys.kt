package com.hawksjamesf.simpleweather.data.bean.home

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
data class Sys(
        var type: Int,
        var id: Int,
        var message: Float,
        var country: String,
        var sunrise: Int,
        var sunset: Int,
        var pod: String
) {
}