package com.hawksjamesf.simpleweather.data.bean

import com.hawksjamesf.simpleweather.data.bean.weather.City

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/27/2018  Thu
 */
data class ListRes<T>(
        var cod: String,
        var message: Float,
        var cnt: Int,
        var list: MutableList<T>,
        var city: City


) {
}