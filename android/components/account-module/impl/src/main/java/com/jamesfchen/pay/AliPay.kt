package com.jamesfchen.pay

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.jamesfchen.export.pay.AliPayInfo
import com.jamesfchen.export.pay.IPayCallback
import kotlin.concurrent.thread

/**
 * 集成sdk：https://opendocs.alipay.com/open/204/105296/
 * SDK下载地址：https://docs.open.alipay.com/54/104509
 */
class AliPay : IPay<AliPayInfo> {
    companion object {
        private const val SDK_PAY_FLAG = 6406
    }

    private lateinit var payInfo: AliPayInfo
    private lateinit var payCallback: IPayCallback
    private val mHandler: Handler = UH()

    /**
     * 参数orderInfo:app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D
     * resultStatus响应码
     * 9000   订单支付成功。
     * 8000   正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态。
     * 4000   订单支付失败。
    5000   重复请求。
    6001   用户中途取消。
    6002   网络连接出错。
    6004   支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态。
    其他    其他支付错误。

    支付宝sdk提到同步通知与异步通知，为了确保可靠性，最好让支付宝服务端异步通知商户服务
     */
    override fun pay(activity: Activity, payInfo: AliPayInfo, payCallback: IPayCallback) {
        if (TextUtils.isEmpty(payInfo.orderInfo)) {
            payCallback.failed(RespCode.CODE_ILLEGAL_ARGURE, RespCode.MESSAGE_ILLEGAL_ARGURE)
            return
        }
        this.payInfo = payInfo
        this.payCallback = payCallback
        // 必须异步调用
        thread {
            val alipay = PayTask(activity)
            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = alipay.payV2(payInfo.orderInfo, true)
            mHandler.sendMessage(msg)
        }
    }

    inner class UH : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = AliPayResult(msg.obj as Map<String, String?>)

                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    // 同步返回需要验证的信息
                    val resultInfo = payResult.result
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档:
                    //https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.IXE2Zj&treeId=59&articleId=103671&docType=1
                    val resultStatus = payResult.resultStatus
                    when {
                        TextUtils.equals(resultStatus, RespCode.ResultCode.CODE_SUCCESS) -> {
                            payCallback.success()
                        }
                        TextUtils.equals(resultStatus, RespCode.ResultCode.CODE_CANCEL) -> {
                            payCallback.cancel()
                        }
                        else -> {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            payCallback.failed(
                                RespCode.ResultCode.getIntCodeByString(resultStatus),
                                RespCode.ResultCode.getTextByCode(resultStatus)
                            )
                        }
                    }
                }
                else -> {
                    //nothing
                }
            }
        }
    }
}
