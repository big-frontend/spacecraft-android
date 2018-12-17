package com.hawksjamesf.network.data.source.remote

import com.hawksjamesf.network.data.bean.ListRes
import com.hawksjamesf.network.data.bean.home.WeatherData
import com.hawksjamesf.network.data.source.WeatherDataSource
import io.reactivex.Observable
import io.reactivex.Single
import kotlin.reflect.KClass

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
class RemoteWeatherDataSource : WeatherDataSource, AbstractApi<WeatherApi>() {
    override lateinit var api: WeatherApi

    override fun getClass(): KClass<WeatherApi> = WeatherApi::class

    override fun getCurrentWeatherDate(city: String): Single<WeatherData> {
        return api.getCurrentWeatherDate(city)

    }

    override fun getFiveData(city: String): Observable<ListRes<WeatherData>> {
        return api.getFiveData(city)

    }
}