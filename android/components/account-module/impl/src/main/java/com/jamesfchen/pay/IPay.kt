package com.jamesfchen.pay

import android.app.Activity
import com.jamesfchen.export.pay.AliPayInfo
import com.jamesfchen.export.pay.IPayCallback
import com.jamesfchen.export.pay.WXPayInfo
import com.jamesfchen.ibc.Api

interface IPay<T> {
    fun pay(activity: Activity, payInfo: T, payCallback: IPayCallback)
}

@Api
class Pay : com.jamesfchen.export.pay.IPay() {
    override fun wxpay(a: Activity, payInfo: WXPayInfo, callback: IPayCallback) =
        WXPay.getInstance().pay(a, payInfo, callback)

    override fun alipay(a: Activity, payInfo: AliPayInfo, callback: IPayCallback) =
        AliPay().pay(a, payInfo, callback)

}

/**
 * 创建pay包，主要用于封装支付宝支付、微信支付、百度支付、oppo支付等支付。
 * 抽象出支付接口让业务层只需要关注支付参数与回调信息。
 */
object CPay {
    @JvmStatic
    fun <T> pay(
        a: Activity, payWay: IPay<T>, payInfo: T, callback: IPayCallback
    ) = payWay.pay(a, payInfo, callback)

    @JvmStatic
    fun wxpay(
        a: Activity, payInfo: WXPayInfo, callback: IPayCallback
    ) = pay(a, WXPay.getInstance(), payInfo, callback)

    @JvmStatic
    fun alipay(a: Activity, payInfo: AliPayInfo, callback: IPayCallback) =
        pay(a, AliPay(), payInfo, callback)

}