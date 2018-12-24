package com.hawksjamesf.common.util

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/25/2018  Thu
 */

typealias OnSubscribe = (Disposable) -> Unit

typealias OnSuccess<T> = (T) -> Unit

typealias OnComplete = () -> Unit
typealias OnError = (Throwable) -> Unit

inline fun Completable.subscribeBy(
        crossinline onSubscribe: OnSubscribe = { },
        crossinline onComplete: OnComplete = { },
        crossinline onError: OnError = { }
) {
    subscribe(object : CompletableObserver {
        override fun onComplete() = onComplete()

        override fun onSubscribe(d: Disposable) = onSubscribe(d)

        override fun onError(e: Throwable) = onError(e)
    })

}

inline fun <T> Single<T>.subscribeBy(
        crossinline onSuccess: OnSuccess<T> = {},
        crossinline onSubscribe: OnSubscribe,
        crossinline onError: OnError = {}

) {
    subscribe(object : SingleObserver<T> {
        override fun onSuccess(t: T) = onSuccess(t)

        override fun onSubscribe(d: Disposable) = onSubscribe(d)

        override fun onError(e: Throwable) = onError(e)

    })
}
