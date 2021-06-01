package com.jamesfchen.network.proxy

import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 四月/28/2021  星期三
 * 使用直连或者http代理，可以使用改工厂模式去构建Socket
 */
class MySocketFactory : SocketFactory() {
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

}