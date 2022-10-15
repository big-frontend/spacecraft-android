package com.jamesfchen.export.pay

data class WXPayInfo(
    val sign: String?,
    val timestamp: String?,
    val partnerid: String?,
    val packageValue: String?,
    val appid: String?,
    val nonceStr: String?,
    val prepayId: String?
)
