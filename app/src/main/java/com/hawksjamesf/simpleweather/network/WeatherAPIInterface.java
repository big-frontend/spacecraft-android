package com.hawksjamesf.simpleweather.network;

import com.hawksjamesf.simpleweather.bean.WeatherData;
import com.hawksjamesf.simpleweather.bean.ListRes;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public interface WeatherAPIInterface {
//    realtime url
//        https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/realtime.json
//
//        https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/realtime.jsonp?callback=MYCALLBACK
//
//    fifteen forecast url
//        https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json
//
//        https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.jsonp?callback=MYCALLBACK


    @GET("v2/TAkhjf8d1nlSlspN/121.6544,25.1552/{filename}.json")
    Call<RealTimeBean> getRealTimeData(@Path("filename") String filename);

//    @GET("v2/TAkhjf8d1nlSlspN/121.6544,25.1552/{filename}.json")
//    Call<FifteenBean>  getFifteenData(@Path("filename") String filename);


    @GET("/data/2.5/weather")
    Observable<Response<WeatherData>> getCurrentWeatherDate(@Query("q") String city);

    /**
     * Call 5 day / 3 hour forecast data
     * By city name
     * api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
     */
    @GET("/data/2.5/forecast")
    Observable<Response<ListRes<WeatherData>>> getFiveData(@Query("q") String city);


}
