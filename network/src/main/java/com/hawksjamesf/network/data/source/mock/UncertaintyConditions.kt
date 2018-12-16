package com.hawksjamesf.network.data.source.mock;

import com.hawksjamesf.network.data.bean.signin.ClientException
import io.reactivex.Single
import java.util.concurrent.TimeUnit


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/22/2018  Mon
 */
open class UncertaintyConditions(private val mUncertaintyParams: UncertaintyParams = UncertaintyParams()) {

    protected fun uncertainty(): Single<Unit> {
        return Single.just(Unit)
                .uncertainNoConnectionError()
                .uncertainDelay()
                .uncertainUnknownError()
    }


    private fun <T> Single<T>.uncertainNoConnectionError(): Single<T> {
        val shouldThrow = Math.random() < mUncertaintyParams.chanceOfFailingWithNoConnectionError
        return map { if (shouldThrow) throw ClientException.NoConnection else it }
    }

    private fun <T> Single<T>.uncertainUnknownError(): Single<T> {
        val shouldThrow = Math.random() < mUncertaintyParams.chanceOfFailingWithUnknownError
        return map { if (shouldThrow) throw ClientException.Unknown else it }
    }

    private fun <T> Single<T>.uncertainDelay(): Single<T> {
        val average = mUncertaintyParams.averageResponseDelayInMillis
        val deviation = (Math.random() - 0.5) * mUncertaintyParams.responseDelayDeviationInMillis
        val delayAmount = (average + deviation).coerceAtLeast(0.0).toLong()
        return if (delayAmount != 0L) delay(delayAmount, TimeUnit.MILLISECONDS) else this
    }

    data class UncertaintyParams(

            val chanceOfFailingWithNoConnectionError: Float = 0.0f,

            val chanceOfFailingWithUnknownError: Float = 0.0f,

            val averageResponseDelayInMillis: Long = 0,

            val responseDelayDeviationInMillis: Long = 0
    )

}
