package com.hawksjamesf.simpleweather.ui.home;

import com.hawksjamesf.simpleweather.ui.mvp.RxPresenter;

import java.util.HashMap;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks.jamesf
 * @since: 1/24/18
 */

public class HomePresenter extends RxPresenter<HomeActivity> implements HomeContract.Presenter {

    public HomePresenter() {
    }

    @Override
    public void load() {


        HashMap<String, String> map = new HashMap<>();
//        DataRequestFactory.create()
//                .all(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn()
    }
}
