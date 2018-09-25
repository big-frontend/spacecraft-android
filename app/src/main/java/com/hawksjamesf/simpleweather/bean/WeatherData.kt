package com.hawksjamesf.simpleweather.bean

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
data class WeatherData(
        var coord: Coord,
        var weather: List<Weather>,
        var base: String,
        var main: Main,
        var visibility: Int,
        var wind: Wind,
        var clouds: Clouds,
        var dt: Int,
        var sys: Sys,
        var id: Int,
        var name: String,
        var code: Int

) {

}