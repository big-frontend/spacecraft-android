package com.jamesfchen.source

import com.jamesfchen.modle.*
import io.reactivex.Single

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
interface SignInDataSource : DataSource {
    fun sendCode(sendCodeReq: SendCodeReqBody): Single<SendCodeRespBody>

    fun signUp(signUpReq: SignUpReqBody): Single<Profile>

    fun signIn(signinReq: SignInReqBody): Single<Profile>

    fun signOut()
}