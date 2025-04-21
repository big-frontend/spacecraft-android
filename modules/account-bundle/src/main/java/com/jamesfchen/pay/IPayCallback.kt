package com.jamesfchen.pay

import androidx.annotation.MainThread

interface IPayCallback {
    @MainThread
    fun success()

    @MainThread
    fun failed(code: Int, message: String?)

    @MainThread
    fun cancel()
}