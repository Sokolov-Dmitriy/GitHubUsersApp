package com.sokolovds.data.cloudDataSource

import okhttp3.OkHttpClient

interface ProvideOkHttpClientBuilder {
    fun httpClientBuilder(): OkHttpClient.Builder

    abstract class Abstract(
        private val provideLoggingInterceptor: ProvideLoggingInterceptor,
        private val oAuthInterceptor: ProvideOAuthInterceptor
    ) : ProvideOkHttpClientBuilder {

        override fun httpClientBuilder() = OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor.interceptor())
            .addInterceptor(oAuthInterceptor)
    }

    class Base(
        provideLoggingInterceptor: ProvideLoggingInterceptor,
        oAuthInterceptor: ProvideOAuthInterceptor
    ) : Abstract(provideLoggingInterceptor, oAuthInterceptor)
}


