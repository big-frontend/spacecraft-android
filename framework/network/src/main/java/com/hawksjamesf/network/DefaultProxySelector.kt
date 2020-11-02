package com.hawksjamesf.network

import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI
import java.util.*


class DefaultProxySelector : ProxySelector() {
    val proxies: MutableList<Proxy> = ArrayList()

    fun addProxy(proxy: Proxy): DefaultProxySelector {
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
