package com.hawksjamesf.simpleweather.data.bean.login

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
enum class ClientState {
    SIGNED_OUT,//已经退出
    SIGNED_IN,//已经登入
    SIGNING_IN,//正在登入
    SIGNING_UP//正在注册
}