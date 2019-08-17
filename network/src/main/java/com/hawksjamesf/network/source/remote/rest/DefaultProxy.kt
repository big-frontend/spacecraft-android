package com.hawksjamesf.network.source.remote.rest

import java.net.Proxy
import java.net.SocketAddress


class DefaultProxy(type: Type?, sa: SocketAddress?) : Proxy(type, sa) {

}
