package com.hawksjamesf.network.data.bean.signin

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
data class SignInReq(
        val mobile: String,
        val password: String
) {
    override fun toString(): String {
        return "SignInReq(mobile='$mobile', password='$password')"
    }
}