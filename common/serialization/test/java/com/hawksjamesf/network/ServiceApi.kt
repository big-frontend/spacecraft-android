package com.hawksjamesf.network

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/17/2019  Sat
 */

interface ServiceApi {
    enum class ServiceState {
        STATE_SUCCESS;

        fun toEnum() {}
    }

    fun emitOnce():Int
    fun emitTwice(vararg number:Int):Int
}