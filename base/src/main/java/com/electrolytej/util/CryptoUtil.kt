@file:JvmName("CryptoUtil")
package com.electrolytej.util

enum class CryptoUtil(var value: String) {
    AESKEY("aesKey"),
    COMMONKEY("commonKey"),
    CONCHKEY("conchKey"),
    WTKEY("wtKey"),
    MAOYANKEY("maoyan_aes_key"),
    OWLKEY("owl_aes_key");
}