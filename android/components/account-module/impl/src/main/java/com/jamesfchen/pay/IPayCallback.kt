package com.jamesfchen.pay

import androidx.annotation.MainThread
import androidx.annotation.Nullable

interface IPayCallback {
    @MainThread
    fun success()

    @MainThread
    fun failed(code: Int, @Nullable message: String?)

    @MainThread
    fun cancel()
}