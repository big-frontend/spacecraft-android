package com.hawksjamesf.myhome;

import com.hawksjamesf.network.NetComponent;
import com.hawksjamesf.myhome.scopes.UserScope;
import com.hawksjamesf.myhome.ui.SplashActivity;
import com.hawksjamesf.myhome.ui.home.HomeActivity;
import com.hawksjamesf.signin.Client;
import com.hawksjamesf.signin.SigInModule;
import com.hawksjamesf.signin.SignInActivity;

import dagger.Component;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
@UserScope
@Component(dependencies = NetComponent.class, modules = {AppModule.class, SigInModule.class}/*more modules*/)
public interface AppComponent {
    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    Client client();

    void inject(SignInActivity signInActivity);


}
