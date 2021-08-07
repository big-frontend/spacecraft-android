package com.jamesfchen.mockserver.control;

import com.jamesfchen.mockserver.Constants;
import com.jamesfchen.mockserver.Control;
import com.jamesfchen.mockserver.util.RestServiceTestHelper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.http.GET;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
@Control
public class WeatherControl {

    @GET("/data/2.5/weather")
    MockResponse getCurrentWeatherDate(RecordedRequest request) {
        String fileName = Constants.CURRENT_DATA_JSON;

        String stringFromFile = "";
        try {
            stringFromFile = RestServiceTestHelper.getStringFromFile(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(stringFromFile);

    }

    /**
     * Call 5 day / 3 hour forecast data
     * By city name
     * api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
     */
    @GET("/data/2.5/forecast")
    MockResponse getFiveData(RecordedRequest request) {
        String fileName = Constants.FIVE_DATA_JSON;
        String stringFromFile = "";
        try {
            stringFromFile = RestServiceTestHelper.getStringFromFile(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(stringFromFile);

    }
}
