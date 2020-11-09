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
class MyExchangeCodec :ExchangeCodec {
    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun connection(): RealConnection? {
        TODO("Not yet implemented")
    }

    override fun createRequestBody(request: Request, contentLength: Long): Sink {
        TODO("Not yet implemented")
    }

    override fun finishRequest() {
        TODO("Not yet implemented")
    }

    override fun flushRequest() {
        TODO("Not yet implemented")
    }

    override fun openResponseBodySource(response: Response): Source {
        TODO("Not yet implemented")
    }

    override fun readResponseHeaders(expectContinue: Boolean): Response.Builder? {
        TODO("Not yet implemented")
    }

    override fun reportedContentLength(response: Response): Long {
        TODO("Not yet implemented")
    }

    override fun trailers(): Headers {
        TODO("Not yet implemented")
    }

    override fun writeRequestHeaders(request: Request) {
        TODO("Not yet implemented")
    }
}