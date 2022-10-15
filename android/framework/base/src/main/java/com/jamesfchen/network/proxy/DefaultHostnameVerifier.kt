package com.jamesfchen.network.proxy

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * 主机验证tls成功之后的证书
 */
class DefaultHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }

}
