package com.hawksjamesf.shenzhou.ui.home;


import android.os.Bundle;

import com.google.gson.Gson;
import com.hawksjamesf.shenzhou.R;
import com.hawksjamesf.shenzhou.App;
import com.hawksjamesf.shenzhou.data.bean.ListRes;
import com.hawksjamesf.shenzhou.data.bean.home.WeatherData;
import com.hawksjamesf.shenzhou.data.source.DataSource;
import com.hawksjamesf.shenzhou.ui.mvp.RxActivity;
import com.orhanobut.logger.Logger;

import io.reactivex.functions.Consumer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeActivity extends RxActivity<HomePresenter> implements HomeContract.View {

    private static final String TAG = "HomeActivity---";
//    private RecyclerView mrvHome;

    DataSource source;

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter.load();

        source = App.getAppComponent().source();
        source.getCurrentWeatherDate("London")
                .subscribe(new Consumer<WeatherData>() {
                    @Override
                    public void accept(WeatherData weatherData) throws Exception {
                        //onSuccess
                        Logger.t(TAG).json(new Gson().toJson(weatherData));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        source.getFiveData("London")
                .subscribe(new Consumer<ListRes<WeatherData>>() {
                    @Override
                    public void accept(ListRes<WeatherData> weatherDataListRes) throws Exception {
                        Logger.t(TAG).json(new Gson().toJson(weatherDataListRes));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }


}
