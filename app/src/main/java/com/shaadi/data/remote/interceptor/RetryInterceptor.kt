package com.shaadi.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetries: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response: Response? = null
        var tryCount = 0

        while (response == null && tryCount < maxRetries) {
            try {
                response = chain.proceed(request)
            } catch (e: Exception) {
                tryCount++
                if (tryCount == maxRetries) {
                    throw e
                }
            }
        }

        return response ?: throw IOException("Max retries reached")
    }
}
