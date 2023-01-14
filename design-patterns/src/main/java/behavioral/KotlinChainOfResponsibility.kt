package behavioral

import jdk.nashorn.internal.codegen.CompilerConstants
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/03/2018  Fri
 */
class KotlinChainOfResponsibility {


}

fun main(args: Array<String>) {
    getResponseWithInterceptorChain(Request())

}

fun getResponseWithInterceptorChain(originalRequest: Request): Response? {
    // Build a full stack of interceptors.
    val interceptors = mutableListOf<Interceptor>()
    interceptors += RetryAndFollowUpInterceptor()
    interceptors += BridgeInterceptor()
    interceptors += CacheInterceptor()
    interceptors += ConnectInterceptor
    interceptors += CallServerInterceptor()

    val chain = RealInterceptorChain(0, request = originalRequest, interceptors = interceptors)

    return try {
        val response = chain.proceed(originalRequest)
        response
    } catch (e: IOException) {
        null
    }
}

class Response {}
class Request {}
class Connection {}
class RetryAndFollowUpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        print("RetryAndFollowUpInterceptor\t")
        return chain.proceed(chain.request())

    }
}

class BridgeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        print("BridgeInterceptor\t")
        return chain.proceed(chain.request())
    }
}

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        print("CacheInterceptor\t")
        return chain.proceed(chain.request())

    }
}

object ConnectInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        print("ConnectInterceptor\t")
        return chain.proceed(chain.request())

    }
}

class CallServerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        print("CallServerInterceptor\t")
        return Response()

    }
}

class RealInterceptorChain(
        private val index: Int,
        private val request: Request,
        private val interceptors: List<Interceptor>
) : Interceptor.Chain {
    override fun request(): Request = request
    override fun proceed(request: Request): Response? {
        val interceptor = interceptors[index]
        val next = RealInterceptorChain(index = index + 1, request = request, interceptors = interceptors)
        val response = interceptor.intercept(next)
        return response
    }


}

interface Interceptor {
    fun intercept(chain: Chain): Response?

    companion object {
        //        inline operator fun invoke(crossinline block: (chain: Chain) -> Response): Interceptor =
//                Interceptor { block(it) }
        operator fun invoke(block: (chain: Chain) -> Response): Interceptor =
                Interceptor { block(it) }
    }

    interface Chain {
        fun request(): Request

        fun proceed(request: Request): Response?

    }
}