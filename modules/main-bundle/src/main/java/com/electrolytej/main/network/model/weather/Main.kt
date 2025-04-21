package com.electrolytej.main.network.model.weather

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
data class Main(
        var temp: Float,//temperature
        var pressure: Float,
        var humidity: Int,//湿度
        var temp_min: Float,
        var temp_max: Float,
        var sea_level: Float,
        var grnd_level: Float,
        var temp_kf: Float
) {
}