package com.hawksjamesf.network.data.source.remote;


import com.hawksjamesf.network.data.bean.ListRes;
import com.hawksjamesf.network.data.bean.home.WeatherData;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public interface WeatherApi {
    @GET("/data/2.5/weather")
    Single<WeatherData> getCurrentWeatherDate(@Query("q") String city);

    /**
     * Call 5 day / 3 hour forecast data
     * By city name
     * api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
     */
    @GET("/data/2.5/forecast")
    Observable<ListRes<WeatherData>> getFiveData(@Query("q") String city);
}
