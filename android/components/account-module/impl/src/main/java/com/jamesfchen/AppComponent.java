package com.jamesfchen;


import com.jamesfchen.scopes.UserScope;
import com.jamesfchen.login.signin.SigInModule;
import com.jamesfchen.login.signin.SignInActivity;

import dagger.Component;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: 2017/7/4
 */
@UserScope
@Component(dependencies = NetComponent.class, modules = {AppModule.class, SigInModule.class}/*more modules*/)
public interface AppComponent {
//    void inject(SplashActivity splashActivity);

//    void inject(HomeActivity homeActivity);

//    Client client();

    void inject(SignInActivity signInActivity);


}
