package com.jamesfchen.api

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.AddTrace
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/28/2019  Sat
 */
class MetricInterceptor : Interceptor {
    val trace = FirebasePerformance.getInstance().newTrace("_metricInterceptor_interceptTraceV2")
    val httpmetricGet = FirebasePerformance.getInstance().newHttpMetric("https://www.google.com", FirebasePerformance.HttpMethod.GET)
    val httpmetricPost = FirebasePerformance.getInstance().newHttpMetric("https://www.google.com", FirebasePerformance.HttpMethod.POST)

//    @AddTrace(name = "MetricInterceptor_interceptTrace", enabled = true)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

//        trace.getLongMetric("")
//        trace.getAttribute("")

//        trace.incrementMetric("")
        trace.start()
        val response = chain.proceed(request)
        trace.stop()
        trace.putAttribute("request_method", request.method)
        trace.putAttribute("request_url", request.url.toString())
        trace.putAttribute("reponse_successful", response.isSuccessful.toString())
        trace.putMetric("response_code", response.code.toLong())
        trace.putMetric("response_delta", response.receivedResponseAtMillis - response.sentRequestAtMillis)

        return response
    }
}