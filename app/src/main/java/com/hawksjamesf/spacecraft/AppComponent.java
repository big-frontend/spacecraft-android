package com.hawksjamesf.spacecraft;

import com.hawksjamesf.spacecraft.data.NetComponent;
import com.hawksjamesf.spacecraft.scopes.UserScope;
import com.hawksjamesf.spacecraft.ui.SplashActivity;
import com.hawksjamesf.spacecraft.ui.home.HomeActivity;
import com.hawksjamesf.spacecraft.ui.signin.Client;

import dagger.Component;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
@UserScope
@Component(dependencies = NetComponent.class, modules = {AppModule.class}/*more modules*/)
public interface AppComponent {
    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    Client client();


}
