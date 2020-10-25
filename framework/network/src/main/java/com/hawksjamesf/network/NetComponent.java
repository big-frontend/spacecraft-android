package com.hawksjamesf.network;


import com.hawksjamesf.network.source.SignInDataSource;
import com.hawksjamesf.network.source.WeatherDataSource;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
@Singleton
@Component(modules = NetModule.class)
public interface NetComponent {

    SignInDataSource getSignInDataSource();
    WeatherDataSource getWeatherDataSource();

//    Client client();

}
