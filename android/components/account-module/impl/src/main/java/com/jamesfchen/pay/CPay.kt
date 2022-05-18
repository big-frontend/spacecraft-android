package com.jamesfchen.pay

import android.app.Activity
import com.jamesfchen.pay.ali.AliPay
import com.jamesfchen.pay.ali.AliPayInfo
import com.jamesfchen.pay.wx.WXPay
import com.jamesfchen.pay.wx.WXPayInfo

/**
 * 创建pay包，主要用于封装支付宝支付、微信支付、百度支付、oppo支付等支付。
 * 抽象出支付接口让业务层只需要关注支付参数与回调信息。
 * //todo:将pay包移动到pay模块，收口快应用、web、百度小程序的支付功能
 */
object CPay {
    @JvmStatic
    fun <T> pay(
        a: Activity,
        payWay: IPay<T>,
        payInfo: T,
        callback: IPayCallback
    ) {
        payWay.pay(a, payInfo, callback)
    }

    @JvmStatic
    fun wxpay(
        a: Activity,
        payInfo: WXPayInfo,
        callback: IPayCallback
    ) {
        pay(a, WXPay.getInstance(), payInfo, callback)
    }

    @JvmStatic
    fun alipay(
        a: Activity,
        payInfo: AliPayInfo,
        callback: IPayCallback
    ) {
        pay(a, AliPay(), payInfo, callback)
    }
}