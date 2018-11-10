package com.hawksjamesf.spacecraft.data;

import com.hawksjamesf.spacecraft.BuildConfig;
import com.hawksjamesf.spacecraft.data.source.SignInDataSource;
import com.hawksjamesf.spacecraft.data.source.WeatherDataSource;
import com.hawksjamesf.spacecraft.data.source.mock.MockSignInDataSource;
import com.hawksjamesf.spacecraft.data.source.mock.MockWeatherDataSource;
import com.hawksjamesf.spacecraft.data.source.mock.UncertaintyConditions;
import com.hawksjamesf.spacecraft.data.source.remote.RemoteWeatherDataSource;
import com.hawksjamesf.spacecraft.data.source.remote.signin.RemoteSignInDataSource;
import com.hawksjamesf.spacecraft.util.Util;

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
