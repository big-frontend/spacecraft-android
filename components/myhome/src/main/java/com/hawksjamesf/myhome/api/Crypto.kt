package com.hawksjamesf.myhome.api

/**
 * cryptography 密码学
 */
object Crypto {
    init {
        System.loadLibrary("client_key")
    }
    external fun getClientKey(string: String?, time: Long): String?
}