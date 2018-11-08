package com.hawksjamesf.spacecraft.data.event

import com.hawksjamesf.spacecraft.data.bean.login.ClientException

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
data class SignUpFailedEvent(
        val mobile: String,
        val password: String,
        val excep: ClientException

) {
}