package com.hawksjamesf.network.data.bean.signin

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
sealed class ClientException : RuntimeException() {
    object NoConnection: ClientException()
    object Unauthorized: ClientException()
    object MobileUnavaible: ClientException()
    object Unknown: ClientException()
    object VerificationCode: ClientException()
}