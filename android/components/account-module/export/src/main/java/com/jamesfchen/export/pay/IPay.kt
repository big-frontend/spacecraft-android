package com.jamesfchen.export.pay

import android.app.Activity
import com.jamesfchen.ibc.cbpc.IExport

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Oct/15/2022  Sat
 */
abstract class IPay : IExport() {
    abstract fun wxpay(a: Activity, payInfo: WXPayInfo, callback: IPayCallback)
    abstract fun alipay(a: Activity, payInfo: AliPayInfo, callback: IPayCallback)
}