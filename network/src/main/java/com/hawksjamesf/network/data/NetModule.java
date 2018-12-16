package com.hawksjamesf.network.data;

import com.hawksjamesf.common.util.Util;
import com.hawksjamesf.network.BuildConfig;
import com.hawksjamesf.network.data.source.SignInDataSource;
import com.hawksjamesf.network.data.source.WeatherDataSource;
import com.hawksjamesf.network.data.source.mock.MockSignInDataSource;
import com.hawksjamesf.network.data.source.mock.MockWeatherDataSource;
import com.hawksjamesf.network.data.source.mock.UncertaintyConditions;
import com.hawksjamesf.network.data.source.remote.RemoteWeatherDataSource;
import com.hawksjamesf.network.data.source.remote.signin.RemoteSignInDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
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
        if (BuildConfig.MOCKED_DATA_ACCESS) {
            return new MockSignInDataSource(Util.getApp(), new UncertaintyConditions.UncertaintyParams(
                    0f, 0, 1500L, 500L
            ));
        } else {
            return new RemoteSignInDataSource();
        }
    }


    @Provides
    @Singleton
    public WeatherDataSource provideWeatherDataSource() {
        if (BuildConfig.MOCKED_DATA_ACCESS) {
            return new MockWeatherDataSource(Util.getApp(), new UncertaintyConditions.UncertaintyParams(
                    0f, 0, 1500L, 500L
            ));
        } else {
            return new RemoteWeatherDataSource();
        }
    }

//    @Provides
//    @Singleton
//    public Client provideClient() {
//        return new Client(null);
//
//    }
}
