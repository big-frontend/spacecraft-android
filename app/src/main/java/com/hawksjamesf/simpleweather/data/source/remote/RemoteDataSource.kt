package com.hawksjamesf.simpleweather.data.source.remote

import com.hawksjamesf.simpleweather.BuildConfig
import com.hawksjamesf.simpleweather.data.bean.ListRes
import com.hawksjamesf.simpleweather.data.bean.home.WeatherData
import com.hawksjamesf.simpleweather.data.bean.login.*
import com.hawksjamesf.simpleweather.data.source.DataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/22/2018  Mon
 */
object RemoteDataSource : DataSource {
    var api: WeatherAPIInterface

    init {
        api = Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_URL)
                //                .baseUrl("http://localhost:50195")
                .client(OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(URLInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(ObservableOrMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIInterface::class.java)

    }

    override fun getCurrentWeatherDate(city: String): Single<WeatherData> {
        return api.getCurrentWeatherDate(city)

    }

    override fun getFiveData(city: String): Observable<ListRes<WeatherData>> {
        return api.getFiveData(city)

    }

    override fun sendCode(sendCodeReq: SendCodeReq): Single<SendCodeResp> {
        return api.sendCode(sendCodeReq)
    }

    override fun signUp(signUpReq: SignUpReq): Single<Profile> {
        return api.signUp(signUpReq)
    }

    override fun signIn(loginReq: SignInReq): Single<Profile> {
        return api.login(loginReq)
    }

    override fun signOut() {

    }
}