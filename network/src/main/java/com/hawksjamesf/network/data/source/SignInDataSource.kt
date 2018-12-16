package com.hawksjamesf.network.data.source

import com.hawksjamesf.network.data.bean.signin.*
import io.reactivex.Single

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
interface SignInDataSource : DataSource {
    fun sendCode(sendCodeReq: SendCodeReq): Single<SendCodeResp>

    fun signUp(signUpReq: SignUpReq): Single<Profile>

    fun signIn(signinReq: SignInReq): Single<Profile>

    fun signOut()
}