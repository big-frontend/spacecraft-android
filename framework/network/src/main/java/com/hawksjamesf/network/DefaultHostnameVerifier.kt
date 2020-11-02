package com.hawksjamesf.network

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class DefaultHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }

}
