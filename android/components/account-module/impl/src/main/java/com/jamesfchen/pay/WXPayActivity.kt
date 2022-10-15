package com.jamesfchen.pay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.MainThread
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


class WXPayActivity : Activity(), IWXAPIEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WXPay.getInstance().getWXApi().handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        WXPay.getInstance().getWXApi().handleIntent(intent, this)
    }

    override fun onReq(p0: BaseReq) {
        //nothing
    }
    @MainThread
    override fun onResp(resp: BaseResp?) {
        if (resp is PayResp && resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WXPay.getInstance().onResp(resp.errCode, resp.errStr)
        }
        finish()
    }
}