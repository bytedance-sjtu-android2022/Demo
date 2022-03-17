package com.bytedance.jstu.demo.lesson5.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TimeConsumeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.nanoTime()
        val resp = chain.proceed(chain.request())
        val endTime = System.nanoTime()
        val url = chain.request().url.toString()
        Log.d(
            "Lesson05/TimeConsume",
            "request:$url, cost time: ${(endTime - startTime) / 1000000} ms."
        )
        return resp
    }
}