package com.hawksjamesf.shenzhou;

import com.hawksjamesf.shenzhou.data.source.DataSource;
import com.hawksjamesf.shenzhou.ui.SplashActivity;
import com.hawksjamesf.shenzhou.ui.home.HomeActivity;
import com.hawksjamesf.shenzhou.ui.login.Client;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */

@Singleton
@Component(modules = AppModule.class/*more modules*/)
public interface AppComponent {
    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    DataSource source();

    Client client();
}
