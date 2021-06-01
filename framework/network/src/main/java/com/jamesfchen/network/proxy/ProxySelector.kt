package com.jamesfchen.network.proxy

import java.io.IOException
import java.net.*
import java.net.ProxySelector
import java.util.*


class ProxySelector : ProxySelector() {
    companion object {
        //SocksProxy
        fun socksProxy(serverName:String,serverPort:Int): Proxy {
            return Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved(serverName, serverPort))
        }
        fun httpProxy(serverName:String,serverPort:Int): Proxy {
            return Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(serverName, serverPort))
        }
    }
    val proxies: MutableList<Proxy> = ArrayList()

    fun addProxy(proxy: Proxy): com.jamesfchen.network.proxy.ProxySelector {
        proxies.add(proxy)
        return this
    }

    override fun select(uri: URI?): MutableList<Proxy> {
        return if (uri?.scheme.equals("http") || uri?.scheme.equals("https"))
            proxies
        else
            Collections.singletonList(Proxy.NO_PROXY)
    }

    override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {

    }

}
