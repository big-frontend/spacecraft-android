package com.jamesfchen.network

import okhttp3.Dns
import java.net.InetAddress

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/10/2019  Sat
 */
class DefaultDns : Dns {
    override fun lookup(hostname: String): MutableList<InetAddress> {
        val addresses = Dns.SYSTEM.lookup(hostname)
        return mutableListOf(addresses[0], addresses[0]);
//        return InetAddress.getAllByName(hostname).toMutableList()
    }


}