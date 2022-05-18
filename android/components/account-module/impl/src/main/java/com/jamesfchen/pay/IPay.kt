package com.jamesfchen.pay

import android.app.Activity

interface IPay<T> {
    fun pay(activity: Activity, payInfo: T, payCallback: IPayCallback)
}