package com.jamesfchen.login.modle

import com.google.gson.annotations.SerializedName

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
data class SignUpReqBody(
        @SerializedName("id")
        val profileId: Int? = -1,
        val password: String,
        val verificationCode: Int
)