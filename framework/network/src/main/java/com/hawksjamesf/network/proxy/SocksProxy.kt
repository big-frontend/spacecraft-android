package com.hawksjamesf.network.proxy

import java.net.InetSocketAddress
import java.net.Proxy
import java.net.SocketAddress


class SocksProxy(type: Type?, sa: SocketAddress?) : Proxy(type, sa) {
    companion object {
        fun proxy(serverName:String,serverPort:Int): Proxy {
            return Proxy(Type.SOCKS, InetSocketAddress.createUnresolved(serverName, serverPort))
        }
    }
}
