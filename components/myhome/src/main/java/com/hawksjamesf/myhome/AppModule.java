package com.hawksjamesf.myhome;

import com.hawksjamesf.loader.App;
import com.hawksjamesf.myhome.scopes.UserScope;
import com.hawksjamesf.signin.Client;
import com.hawksjamesf.network.source.SignInDataSource;

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
