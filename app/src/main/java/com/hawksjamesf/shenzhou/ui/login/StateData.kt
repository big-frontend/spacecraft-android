package com.hawksjamesf.shenzhou.ui.login

import com.hawksjamesf.shenzhou.data.bean.login.ClientState
import com.hawksjamesf.shenzhou.data.bean.login.Profile
import io.reactivex.disposables.Disposable

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
data class StateData(
        val signinginDisposable: Disposable? = null,
        val signingupDisposable: Disposable? = null,
        val profile: Profile? = null
) {
    val state: ClientState
        get() = when {
            signinginDisposable != null -> ClientState.SIGNING_IN
            signingupDisposable != null -> ClientState.SIGNING_UP
            profile != null -> ClientState.SIGNED_IN
            else -> ClientState.SIGNED_OUT
        }
}