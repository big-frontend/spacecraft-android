package com.jamesfchen.pay.ali
class AliPayResult(rawResult: Map<String, String?>) {
    val resultStatus: String? by rawResult
    val result: String? by rawResult
    val memo: String? by rawResult
    override fun toString(): String {
        return ("resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}")
    }
}