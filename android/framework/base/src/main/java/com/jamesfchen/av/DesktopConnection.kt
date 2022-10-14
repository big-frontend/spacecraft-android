package com.jamesfchen.av

import android.net.LocalSocket
import android.net.LocalSocketAddress
import java.io.Closeable
import java.io.FileDescriptor
import java.io.IOException

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/20/2020  Thu
 */
class DesktopConnection(

) : Closeable {
    companion object{
         const val SOCKET_NAME = "river"
        @Throws(IOException::class)
        fun connect(abstractName: String): LocalSocket {
            val localSocket = LocalSocket()
            localSocket.connect(LocalSocketAddress(abstractName))
            return localSocket
        }

    }
    private lateinit var videoSocket: LocalSocket
    private lateinit var videoFd: FileDescriptor
    override fun close() {

    }


}