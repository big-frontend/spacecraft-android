package com.hawksjamesf.simpleweather;

import com.hawksjamesf.simpleweather.ui.HomeService;
import com.hawksjamesf.simpleweather.ui.SplashAcvitivy;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/7/4
 */
@Singleton
@Component(modules = AppModule.class/*more modules*/)
public interface AppComponent {
    void inject(SplashAcvitivy splashAcvitivy);
    void inject(HomeService homeService);

}
