package com.hawksjamesf;

import com.hawksjamesf.common.util.Util;
import com.hawksjamesf.source.SignInDataSource;
import com.hawksjamesf.source.mock.MockSignInDataSource;
import com.hawksjamesf.source.mock.UncertaintyConditions;
import com.hawksjamesf.source.remote.rest.signin.RemoteSignInDataSource;

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
