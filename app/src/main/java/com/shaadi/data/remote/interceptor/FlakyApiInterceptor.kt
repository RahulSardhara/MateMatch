package com.shaadi.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.random.Random


class FlakyApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val chance = Random.nextInt(100)
        if (chance < 30) {
            throw IOException("Simulated network failure")
        }
        return chain.proceed(chain.request())
    }
}