package com.jamesfchen.pay

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import androidx.annotation.GuardedBy
import com.jamesfchen.pay.IPayCallback
import com.jamesfchen.pay.WXPayInfo
import com.jamesfchen.pay.RespCode.WXErrCodeEx.getMessageByCode
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 *  接入参考文档：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN
    sdk下载文档：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419319167&token=&lang=zh_CN
    微信支付文档：https://pay.weixin.qq.com/wiki/doc/apiv3/open/pay/chapter2_5_2.shtml#part-6
 */
class WXPay private constructor() : IPay<WXPayInfo> {
    companion object {
        private val objectLock = Any()

        @GuardedBy("objectLock")
        private var sInstance: WXPay? = null
        fun getInstance(): WXPay {
            if (sInstance == null) {
                synchronized(objectLock) {
                    if (sInstance == null) {
                        sInstance = WXPay()
                    }
                }
            }
            return sInstance!!
        }
    }

    private var initializated = false
    private lateinit var mWXApi: IWXAPI
    private lateinit var payInfo: WXPayInfo
    private var sPayCallback: IPayCallback? = null
    fun getWXApi(): IWXAPI {
        return mWXApi
    }

    private fun initWXApi(context: Context, appId: String) {
        mWXApi = WXAPIFactory.createWXAPI(context.applicationContext, appId)
        mWXApi.registerApp(appId)
        initializated = true
    }

    /**
     * app调起支付的各个参数的定义：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_4.shtml
            微信响应码
            名称	描述	解决方案
            0	成功	展示页面成功
            -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常原因等
            -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP
     */
    override fun pay(activity: Activity, payInfo: WXPayInfo, payCallback: IPayCallback) {
        this.payInfo = payInfo;
        sPayCallback = payCallback

        if (TextUtils.isEmpty(payInfo.appid) || TextUtils.isEmpty(payInfo.partnerid)
            || TextUtils.isEmpty(payInfo.prepayId) || TextUtils.isEmpty(payInfo.packageValue)
            || TextUtils.isEmpty(payInfo.nonceStr) || TextUtils.isEmpty(payInfo.timestamp)
            || TextUtils.isEmpty(payInfo.sign)
        ) {
            sPayCallback?.failed(RespCode.CODE_ILLEGAL_ARGURE, RespCode.MESSAGE_ILLEGAL_ARGURE)
            return
        }
        if (!initializated) {
            initWXApi(activity.applicationContext, payInfo.appid!!)
        }
        if (!mWXApi.isWXAppInstalled || mWXApi.wxAppSupportAPI < Build.PAY_SUPPORTED_SDK_INT) {
            sPayCallback?.failed(
                RespCode.WXErrCodeEx.CODE_UNSUPPORT,
                getMessageByCode(RespCode.WXErrCodeEx.CODE_UNSUPPORT)
            )
            return
        }

        val req = PayReq()
        req.appId = payInfo.appid
        req.partnerId = payInfo.partnerid
        req.prepayId = payInfo.prepayId
        req.packageValue = payInfo.packageValue
        req.nonceStr = payInfo.nonceStr
        req.timeStamp = payInfo.timestamp
        req.sign = payInfo.sign

        mWXApi.sendReq(req)
    }

    fun onResp(errorCode: Int, errorMsg: String?) {
        sPayCallback?.let {
            when (errorCode) {
                BaseResp.ErrCode.ERR_OK -> {
                    sPayCallback?.success()
                }
                BaseResp.ErrCode.ERR_USER_CANCEL -> {
                    sPayCallback?.cancel()
                }
                else -> {
                    sPayCallback?.failed(errorCode, errorMsg)
                }
            }
            sPayCallback = null
        }
    }
}