package com.jamesfchen.myhome.screen.home;

import com.google.gson.Gson;
import com.jamesfchen.mvp.RxPresenter;
import com.jamesfchen.myhome.network.RetrofitHelper;
import com.jamesfchen.myhome.network.api.WeatherApi;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: jamesfchen
 * @since: 1/24/18
 */

public class HomePresenter extends RxPresenter<HomeActivity> implements HomeContract.Presenter {
    private static final String TAG = "HomeActivity---";
    public HomePresenter() {
    }

    @Override
    public void load() {


        HashMap<String, String> map = new HashMap<>();
//        DataRequestFactory.create()
//                .all(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn()
        WeatherApi weatherApi = RetrofitHelper.createWeatherApi();
        weatherApi.getCurrentWeatherDate("London")
                .subscribe(weatherData -> {
                    //onSuccess
                    Logger.t(TAG).json(new Gson().toJson(weatherData));
                    int a= 1;

                }, throwable -> {
                    int a= 1;
                    Logger.t(TAG).json(new Gson().toJson(throwable.getStackTrace()));
                });

        weatherApi.getFiveData("London")
                .subscribe(weatherDataListRes -> {
                    int a= 1;
                    Logger.t(TAG).json(new Gson().toJson(weatherDataListRes));
                }, throwable -> {
                    int a= 1;
                    Logger.t(TAG).json(new Gson().toJson(throwable.getStackTrace()));

                });
    }
}
