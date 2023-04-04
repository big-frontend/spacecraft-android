package com.jamesfchen.network.proxy

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * 如果开启隧道之前需要鉴权
 * <pre>
 *     {@code
 *       private fun createTunnelRequest(): Request {
 *val proxyConnectRequest = Request.Builder()
 *.url(route.address.url)
 *.method("CONNECT", null)
 *.header("Host", route.address.url.toHostHeader(includeDefaultPort = true))
 *.header("Proxy-Connection", "Keep-Alive") // For HTTP/1.0 proxies like Squid.
 *.header("User-Agent", userAgent)
 *.build()
 *
 *val fakeAuthChallengeResponse = Response.Builder()
 *.request(proxyConnectRequest)
 *.protocol(Protocol.HTTP_1_1)
 *.code(HTTP_PROXY_AUTH)
 *.message("Preemptive Authenticate")
 *.body(EMPTY_RESPONSE)
 *.sentRequestAtMillis(-1L)
 *.receivedResponseAtMillis(-1L)
 *.header("Proxy-Authenticate", "OkHttp-Preemptive")
 *.build()
 *
 *val authenticatedRequest = route.address.proxyAuthenticator
 *.authenticate(route, fakeAuthChallengeResponse)
 *
 *return authenticatedRequest ?: proxyConnectRequest
 *}
 *     }</pre>
 *
 */
class ProxyAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return null
    }
}
