package com.hawksjamesf.spacecraft.ui.home;


import android.os.Bundle;

import com.google.gson.Gson;
import com.hawksjamesf.common.mvp.RxActivity;
import com.hawksjamesf.network.data.bean.ListRes;
import com.hawksjamesf.network.data.bean.home.WeatherData;
import com.hawksjamesf.network.data.source.WeatherDataSource;
import com.hawksjamesf.spacecraft.App;
import com.hawksjamesf.spacecraft.R;
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

    WeatherDataSource source;

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter.load();

        source = App.getNetComponet().getWeatherDataSource();
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

//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentByTag("tag_navigation_host");
//        if (navHostFragment == null) {
//            navHostFragment = NavHostFragment.create(R.navigation.nav_graph);
//        }
//
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fl_nav_host, navHostFragment, "tag_navigation_host")
//                .setPrimaryNavigationFragment(navHostFragment)// this is the equivalent to app:defaultNavHost="true"
//                .commitNowAllowingStateLoss();
    }


    @Override
    public boolean onSupportNavigateUp() {
//        return Navigation.findNavController(R.id.).navigateUp();
        return super.onSupportNavigateUp();
    }


}
