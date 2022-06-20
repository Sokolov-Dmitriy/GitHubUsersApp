package com.sokolovds.data.cloudDataSource

import okhttp3.Headers
import okhttp3.Interceptor

internal class Headers() : Interceptor {

    private val headers: MutableMap<String, String> = mutableMapOf()

    fun addHeader(name: String, value: String): com.sokolovds.data.cloudDataSource.Headers {
        headers[name] = value
        return this
    }

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain
            .request()
            .newBuilder()
            .headers(Headers.of(headers))
            .build()
    )
}