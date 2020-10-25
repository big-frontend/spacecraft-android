package com.hawksjamesf.network;

import android.content.Context;

import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.Gson;
import com.hawksjamesf.mockserver.model.WeatherData;
import com.hawksjamesf.network.gson.ListRes;
import com.hawksjamesf.network.source.remote.rest.weather.WeatherApi;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.mock.BehaviorDelegate;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class WeatherApiImpl implements WeatherApi {
    BehaviorDelegate<WeatherApi> delegate;
    Context context;

    WeatherApiImpl(Context context, BehaviorDelegate<WeatherApi> delegate) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    public Single<WeatherData> getCurrentWeatherDate(String city) {
        String file = null;
        try {

            file = ResourceUtils.readAssets2String("current_data.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        WeatherData data = new Gson().fromJson(file, WeatherData.class);
        return delegate.returningResponse(data).getCurrentWeatherDate(city);
    }

    @Override
    public Observable<ListRes<WeatherData>> getFiveData(String city) {
        return null;
    }
}
