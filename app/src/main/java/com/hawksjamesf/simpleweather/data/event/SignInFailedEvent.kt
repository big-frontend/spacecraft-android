package com.hawksjamesf.simpleweather.data.event

import com.hawksjamesf.simpleweather.data.bean.login.ClientException

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
data class SignInFailedEvent(
        val mobile: String,
        val password: String,
        val excep: ClientException

)