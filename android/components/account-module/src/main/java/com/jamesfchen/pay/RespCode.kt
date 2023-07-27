package com.jamesfchen.pay

import com.tencent.mm.opensdk.modelbase.BaseResp

object RespCode {
    //通用code
    const val CODE_ILLEGAL_ARGURE = 1000
    const val MESSAGE_ILLEGAL_ARGURE = "订单参数不合法"

    object WXErrCodeEx : BaseResp.ErrCode {
        //微信定制的code
        const val CODE_UNSUPPORT = 2000
        const val MESSAGE_UNSUPPORT = "未安装微信或者微信版本太低"
        private val sErrorMap = hashMapOf<Int, String>()

        init {
            sErrorMap[CODE_UNSUPPORT] = MESSAGE_UNSUPPORT
            sErrorMap[CODE_ILLEGAL_ARGURE] = MESSAGE_ILLEGAL_ARGURE
        }

        fun getMessageByCode(code: Int): String? {
            return sErrorMap[code]
        }
    }

    object ResultCode {
        private val sErrorMap = hashMapOf<String, String>()
        const val CODE_SUCCESS: String = "9000"
        const val CODE_HANDLING = "8000"
        const val CODE_FAIL = "4000"
        const val CODE_REPEAT = "5000"
        const val CODE_CANCEL = "6001"
        const val CODE_NETWORK = "6002"
        const val CODE_UNKNOWN = "6004"

        private const val TEXT_SUCCESS = "订单支付成功"
        private const val TEXT_HANDLING = "正在处理中"
        private const val TEXT_FAIL = "订单支付失败"
        private const val TEXT_REPEAT = "重复请求"
        private const val TEXT_CANCEL = "用户中途取消"
        private const val TEXT_NETWORK = "网络连接出错"
        private const val TEXT_UNKNOWN = "支付结果未知"
        private const val TEXT_ERROR = "未知错误"
        init {
            sErrorMap[CODE_SUCCESS] = TEXT_SUCCESS
            sErrorMap[CODE_HANDLING] = TEXT_HANDLING
            sErrorMap[CODE_FAIL] = TEXT_FAIL
            sErrorMap[CODE_REPEAT] = TEXT_REPEAT
            sErrorMap[CODE_CANCEL] = TEXT_CANCEL
            sErrorMap[CODE_NETWORK] = TEXT_NETWORK
            sErrorMap[CODE_UNKNOWN] = TEXT_UNKNOWN
        }

        fun getTextByCode(code: String?): String {
            return sErrorMap[code] ?: return TEXT_ERROR
        }

        fun getIntCodeByString(errorCode: String?): Int {
            return errorCode?.toInt()?:-1
        }
    }
}