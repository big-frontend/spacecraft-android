package com.electrolytej.main.network.model.weather

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/27/2018  Thu
 */
data class City(
    var id: Int,
    var name: String,
    var coord: Coordinate,
    var country: String,
    var population: Int
) {
}