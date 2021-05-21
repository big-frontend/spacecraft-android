package com.hawksjamesf.network.source.remote.rest.signin

import com.hawksjamesf.modle.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/11/2018  Sun
 */
interface SignInApi {

    @POST("/sendcode")
    abstract fun sendCode(@Body reqbody: SendCodeReqBody): Single<SendCodeRespBody>

    @POST("/signup")
    abstract fun signUp(@Body reqbody: SignUpReqBody): Single<Profile>

    @POST("/signin")
    abstract fun signIn(@Body reqbody: SignInReqBody): Single<Profile>
}