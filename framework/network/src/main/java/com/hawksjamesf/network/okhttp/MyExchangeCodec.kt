package com.hawksjamesf.network.okhttp

import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.connection.RealConnection
import okhttp3.internal.http.ExchangeCodec
import okio.Sink
import okio.Source

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/09/2020  Mon
 */
//class MyExchangeCodec : ExchangeCodec {
//    override val connection: RealConnection
//        get() = RealConnection.newTestConnection()
//
//    override fun cancel() {
//    }
//
//    override fun createRequestBody(request: Request, contentLength: Long): Sink {
//    }
//
//    override fun finishRequest() {
//    }
//
//    override fun flushRequest() {
//    }
//
//    override fun openResponseBodySource(response: Response): Source {
//    }
//
//    override fun readResponseHeaders(expectContinue: Boolean): Response.Builder? {
//    }
//
//    override fun reportedContentLength(response: Response): Long {
//    }
//
//    override fun trailers(): Headers {
//    }
//
//    override fun writeRequestHeaders(request: Request) {
//    }
//}