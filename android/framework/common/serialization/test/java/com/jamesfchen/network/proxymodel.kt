package com.jamesfchen.network

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/17/2019  Sat
 */
class ServiceApiImp : ServiceApi {
    override fun emitTwice(vararg number: Int): Int {
        return 0
    }

    override fun emitOnce(): Int {
        return 0
    }

}

class Proxy(val impl: ServiceApiImp) : ServiceApi by impl

fun <T> create(service: Class<T>): T {
    return java.lang.reflect.Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)
    ) { proxy, method, args ->
        println("invoke:" + method.name)

        val start = System.currentTimeMillis()
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        printInfo("耗时:" + (System.currentTimeMillis() - start))

        2
    } as T
}