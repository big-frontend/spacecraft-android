package com.jamesfchen.myhome;

import com.jamesfchen.loader.App;

import dagger.Module;

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

//    @Provides
//    @UserScope
//    public Client provideClient(SignInDataSource dataSource) {
//        return new Client(dataSource);
//
//    }

//    @Provides
//    @UserScope
//    public SignInContract.Presenter provideSignInPresenter(SignInDataSource dataSource) {
//        return new SignInPresenter(dataSource);
//    }
}
