package com.hawksjamesf.spacecraft;

import com.hawksjamesf.network.data.source.SignInDataSource;
import com.hawksjamesf.spacecraft.scopes.UserScope;
import com.hawksjamesf.spacecraft.ui.signin.Client;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
@Module
public class AppModule {

    private App app;

    AppModule(App app) {
        this.app = app;
    }

    @Provides
    @UserScope
    public Client provideClient(SignInDataSource dataSource) {
        return new Client(dataSource);

    }

//    @Provides
//    @UserScope
//    public SignInContract.Presenter provideSignInPresenter(SignInDataSource dataSource) {
//        return new SignInPresenter(dataSource);
//    }
}
