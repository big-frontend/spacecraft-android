package com.jamesfchen;

import com.jamesfchen.common.util.Util;
import com.jamesfchen.source.SignInDataSource;
import com.jamesfchen.source.mock.MockSignInDataSource;
import com.jamesfchen.source.mock.UncertaintyConditions;
import com.jamesfchen.source.remote.rest.signin.RemoteSignInDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/10/2018  Sat
 */
@Module
public class NetModule {
//    public App app;
//
//    NetModule(App app) {
//        this.app = app;
//    }


    @Provides
    @Singleton
    public SignInDataSource provideSignInDataSource() {
        if (false) {
            return new MockSignInDataSource(Util.getApp(), new UncertaintyConditions.UncertaintyParams(
                    0f, 0, 1500L, 500L
            ));
        } else {
            return new RemoteSignInDataSource();
        }
    }


//    @Provides
//    @Singleton
//    public WeatherDataSource provideWeatherDataSource() {
//        if (false) {
//            return new MockWeatherDataSource(Util.getApp(), new UncertaintyConditions.UncertaintyParams(
//                    0f, 0, 1500L, 500L
//            ));
//        } else {
//            return new RemoteWeatherDataSource();
//        }
//    }

//    @Provides
//    @Singleton
//    public Client provideClient() {
//        return new Client(null);
//
//    }
}
