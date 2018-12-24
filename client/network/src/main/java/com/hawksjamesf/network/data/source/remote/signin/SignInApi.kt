package com.hawksjamesf.network.data.source.remote.signin

import com.hawksjamesf.network.data.bean.signin.*
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
    abstract fun sendCode(@Body req: SendCodeReq): Single<SendCodeResp>

    @POST("/signup")
    abstract fun signUp(@Body req: SignUpReq): Single<Profile>

    @POST("/signIn")
    abstract fun login(@Body req: SignInReq): Single<Profile>
}