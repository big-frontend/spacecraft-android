package com.hawksjamesf.myhome.api

import com.hawksjamesf.mockserver.model.WeatherData
import com.hawksjamesf.network.gson.ListRes
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