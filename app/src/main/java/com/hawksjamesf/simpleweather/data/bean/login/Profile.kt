package com.hawksjamesf.simpleweather.data.bean.login

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
data class Profile(
        val id: Int,
        val mobile: String,
        var token: String?,
        var refreshToken: String?
){
    override fun toString(): String {
        return "Profile(id=$id, mobile='$mobile', token=$token, refreshToken=$refreshToken)"
    }
}