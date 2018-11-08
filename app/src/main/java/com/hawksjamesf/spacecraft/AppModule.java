package com.hawksjamesf.spacecraft;

import com.hawksjamesf.spacecraft.data.source.DataSource;
import com.hawksjamesf.spacecraft.data.source.mock.MockDataSource;
import com.hawksjamesf.spacecraft.data.source.remote.RemoteDataSource;
import com.hawksjamesf.spacecraft.ui.login.Client;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
@Module
class AppModule {

    private App app;

    AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    DataSource provideDataSource() {
        if (BuildConfig.MOCKED_DATA_ACCESS) {
            return new MockDataSource(app, new MockDataSource.UncertaintyParams(
                    0f, 0, 1500L, 500L
            ));
        } else {
            return RemoteDataSource.INSTANCE;
        }
    }

    @Provides
    @Singleton
    Client provideClient(DataSource source){
        return new Client(source);

    }
}
