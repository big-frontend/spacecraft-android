package com.hawksjamesf.network.data.source.remote.signin

import com.hawksjamesf.network.data.bean.signin.*
import com.hawksjamesf.network.data.source.SignInDataSource
import com.hawksjamesf.network.data.source.remote.AbstractApi
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