package com.hawksjamesf.simpleweather.data.source.remote;

import com.hawksjamesf.simpleweather.data.bean.ListRes;
import com.hawksjamesf.simpleweather.data.bean.login.Profile;
import com.hawksjamesf.simpleweather.data.bean.home.WeatherData;
import com.hawksjamesf.simpleweather.data.bean.login.SignInReq;
import com.hawksjamesf.simpleweather.data.bean.login.SendCodeReq;
import com.hawksjamesf.simpleweather.data.bean.login.SendCodeResp;
import com.hawksjamesf.simpleweather.data.bean.login.SignUpReq;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("/data/2.5/weather")
    Single<WeatherData> getCurrentWeatherDate(@Query("q") String city);

    /**
     * Call 5 day / 3 hour forecast data
     * By city name
     * api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
     */
    @GET("/data/2.5/forecast")
    Observable<ListRes<WeatherData>> getFiveData(@Query("q") String city);

    @POST("/sendcode")
    Single<SendCodeResp> sendCode(@Body SendCodeReq req);

    @POST("/signup")
    Single<Profile> signUp(@Body SignUpReq req);

    @POST("/signIn")
    Single<Profile> login(@Body SignInReq req);


}
