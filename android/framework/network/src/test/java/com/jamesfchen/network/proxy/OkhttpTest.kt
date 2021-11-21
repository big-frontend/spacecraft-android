package com.jamesfchen.network.proxy

import junit.framework.TestCase
import okhttp3.*
import org.junit.Test
import java.security.KeyStore
import java.util.*
import javax.net.ssl.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/15/2019  Sun
 */
class OkhttpTest : TestCase() {
    override fun setUp() {
        super.setUp()
        val client = OkHttpClient()
    }

    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun proxy() {
        val serverName: String = ""
        val serverPort: Int = 8080
        val credential: String = Credentials.basic("jesse", "password1")
        val challengeSchemes= mutableListOf<String>()
        val (sslSocketFactory, trustManager) = createMetadata()
        //开启了隧道代理 sslSocketFactory+http代理
        val okHttpClient = OkHttpClient.Builder()
                .proxy(ProxySelector.httpProxy(serverName, serverPort))//选择代理的方式http、socks 、 direct
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(DefaultHostnameVerifier())
                .proxyAuthenticator(object :Authenticator{
                    override fun authenticate(route: Route?, response: Response): Request? {
                        val challenges: List<Challenge> = response.challenges()
                        challengeSchemes.add(challenges[0].scheme)
                        return response.request.newBuilder()
                                .header("Proxy-Authorization", credential)
                                .build()
                    }
                })
                .build()
        //开启了非隧道代理，无需验证主机
        val okHttpClient2 = OkHttpClient.Builder()
                .proxy(ProxySelector.httpProxy(serverName, serverPort))//选择代理的方式http、socks 、 direct
                .socketFactory(MySocketFactory())//具体的代理类
                .build()
    }

    data class SocketMetadata(val sslSocketFactory: SSLSocketFactory, val trustManager: X509TrustManager)

    private fun createMetadata(): SocketMetadata {
        //android system ssl(openssl)
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).also {
            it.init(null as KeyStore?)
        }
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        val sslSocketFactory = sslContext.socketFactory

        //third-party platform
        return SocketMetadata(sslSocketFactory, trustManager)
    }
}