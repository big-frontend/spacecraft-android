package com.hawksjamesf.spacecraft.data.source

import com.hawksjamesf.spacecraft.data.bean.ListRes
import com.hawksjamesf.spacecraft.data.bean.home.WeatherData
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