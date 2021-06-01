package com.jamesfchen.source.remote.rest.signin

import com.jamesfchen.modle.*
import com.jamesfchen.source.SignInDataSource
import com.jamesfchen.source.remote.rest.AbstractApi
import io.reactivex.Single
import kotlin.reflect.KClass

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
class RemoteSignInDataSource : SignInDataSource, AbstractApi<SignInApi>() {
    override lateinit var api: SignInApi


    override fun getClass(): KClass<SignInApi> = SignInApi::class
    override fun sendCode(sendCodeReq: SendCodeReqBody): Single<SendCodeRespBody> {
        return api.sendCode(sendCodeReq)
    }

    override fun signUp(signUpReq: SignUpReqBody): Single<Profile> {
        return api.signUp(signUpReq)
    }

    override fun signIn(loginReq: SignInReqBody): Single<Profile> {
        return api.signIn(loginReq)
    }

    override fun signOut() {

    }
}