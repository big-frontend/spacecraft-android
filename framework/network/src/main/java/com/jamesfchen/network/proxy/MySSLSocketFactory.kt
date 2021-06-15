package com.jamesfchen.network.proxy

import okhttp3.ConnectionSpec
import okhttp3.TlsVersion
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.internal.TlsUtil.localhost
import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 四月/28/2021  星期三
 *
 * 如果要使用http的隧道，就需要重写SSLSocketFactory + Proxy为Http，因为http的隧道都是加密
 */
class MySSLSocketFactory : SSLSocketFactory() {
//    private fun createConnectionSpecSelector(vararg connectionSpecs: ConnectionSpec): ConnectionSpecSelector {
////        return ConnectionSpecSelector(asList(connectionSpecs))
//        return  ConnectionSpecSelector(mutableListOf())
//    }

    private var handshakeCertificates: HandshakeCertificates = localhost()
    private fun createSocketWithEnabledProtocols(vararg tlsVersions: TlsVersion): SSLSocket {
        val socket = handshakeCertificates.sslSocketFactory().createSocket() as SSLSocket
        socket.enabledProtocols = javaNames(*tlsVersions)
        return socket
    }

    private fun javaNames(vararg tlsVersions: TlsVersion): Array<String?> {
        val protocols = arrayOfNulls<String>(tlsVersions.size)
        for (i in 0 until tlsVersions.size) {
            protocols[i] = tlsVersions[i].javaName
        }
        return protocols
    }

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket {
//        val connectionSpecSelector: ConnectionSpecSelector = createConnectionSpecSelector(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)
        val socket: SSLSocket = createSocketWithEnabledProtocols(TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
        return socket
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