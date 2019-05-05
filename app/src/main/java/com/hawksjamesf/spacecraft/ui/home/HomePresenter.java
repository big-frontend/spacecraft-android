package com.hawksjamesf.spacecraft.ui.home;

import com.hawksjamesf.common.mvp.RxPresenter;

import java.util.HashMap;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
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
