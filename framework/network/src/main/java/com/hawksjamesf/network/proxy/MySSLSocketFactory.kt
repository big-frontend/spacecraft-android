package com.hawksjamesf.network.proxy

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocketFactory

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 四月/28/2021  星期三
 */
class MySSLSocketFactory : SSLSocketFactory() {
    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(host: String?, port: Int): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(host: InetAddress?, port: Int): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket {
        TODO("Not yet implemented")
    }

    override fun getDefaultCipherSuites(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getSupportedCipherSuites(): Array<String> {
        TODO("Not yet implemented")
    }
}