package com.hawksjamesf.network.source

import com.hawksjamesf.network.gson.ListRes
import com.hawksjamesf.network.home.WeatherData
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
interface WeatherDataSource : DataSource {
    fun getCurrentWeatherDate(city: String): Single<WeatherData>

    fun getFiveData(city: String): Observable<ListRes<WeatherData>>
}