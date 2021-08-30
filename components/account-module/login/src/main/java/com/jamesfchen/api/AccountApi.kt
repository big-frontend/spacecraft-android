package com.jamesfchen.api

import com.jamesfchen.modle.*
import retrofit2.http.Body
import retrofit2.http.POST
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/11/2018  Sun
 */
interface AccountApi {
    companion object{
        fun create(): AccountApi {
            return RetrofitHelper.create(AccountApi::class)
        }
    }

    @POST("/sendcode")
    suspend fun sendCode(@Body reqbody: SendCodeReqBody): SendCodeRespBody

    @POST("/signup")
    suspend fun signUp(@Body reqbody: SignUpReqBody): Profile

    @POST("/signin")
    suspend fun signIn(@Body reqbody: SignInReqBody): Profile
}