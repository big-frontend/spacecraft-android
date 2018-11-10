package com.hawksjamesf.spacecraft.data.source.local;

import com.hawksjamesf.spacecraft.data.bean.ListRes;
import com.hawksjamesf.spacecraft.data.bean.login.Profile;
import com.hawksjamesf.spacecraft.data.bean.home.WeatherData;
import com.hawksjamesf.spacecraft.data.bean.login.SignInReq;
import com.hawksjamesf.spacecraft.data.bean.login.SendCodeReq;
import com.hawksjamesf.spacecraft.data.bean.login.SendCodeResp;
import com.hawksjamesf.spacecraft.data.bean.login.SignUpReq;
import com.hawksjamesf.spacecraft.data.source.DataSource;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/22/2018  Mon
 */
public class LocalDataSource implements DataSource {
    private static LocalDataSource dataSource;

    public static LocalDataSource getInstance() {
        if (dataSource == null) {
            synchronized (LocalDataSource.class) {
                if (dataSource == null) {
                    return new LocalDataSource();
                }
            }
        }
        return dataSource;
    }

    private LocalDataSource() {
    }


    @NotNull
    @Override
    public Single<WeatherData> getCurrentWeatherDate(@NotNull String city) {
        return null;
    }

    @NotNull
    @Override
    public Observable<ListRes<WeatherData>> getFiveData(@NotNull String city) {
        return null;
    }

    @NotNull
    @Override
    public Single<SendCodeResp> sendCode(@NotNull SendCodeReq sendCodeReq) {
        return null;
    }

    @NotNull
    @Override
    public Single<Profile> signUp(@NotNull SignUpReq signUpReq) {
        return null;
    }

    @NotNull
    @Override
    public Single<Profile> signIn(@NotNull SignInReq loginReq) {
        return null;
    }

    @Override
    public void signOut() {

    }
}
