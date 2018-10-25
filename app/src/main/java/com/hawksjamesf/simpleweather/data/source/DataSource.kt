package com.hawksjamesf.simpleweather.data.source

import com.hawksjamesf.simpleweather.data.bean.ListRes
import com.hawksjamesf.simpleweather.data.bean.home.WeatherData
import com.hawksjamesf.simpleweather.data.bean.login.*
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/22/2018  Mon
 *
 * 有了DataSource借口之后，应用就可以在数据源这块更加当前测试环境切换，目前可选的数据源有本地mock数据、real远程服务器数据、real local server本地服务器数据
 */
interface DataSource {
    fun getCurrentWeatherDate(city: String): Single<WeatherData>

    fun getFiveData(city: String): Observable<ListRes<WeatherData>>

    fun sendCode(sendCodeReq: SendCodeReq): Single<SendCodeResp>

    fun signUp(signUpReq: SignUpReq): Single<Profile>

    fun signIn(signinReq: SignInReq): Single<Profile>

    fun signOut()

}