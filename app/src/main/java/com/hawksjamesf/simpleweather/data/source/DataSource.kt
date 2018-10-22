package com.hawksjamesf.simpleweather.data.source

import com.hawksjamesf.simpleweather.data.bean.ListRes
import com.hawksjamesf.simpleweather.data.bean.WeatherData
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/22/2018  Mon
 */
interface DataSource {
    fun getCurrentWeatherDate(city: String): Single<WeatherData>

    fun getFiveData(city: String): Observable<ListRes<WeatherData>>
}