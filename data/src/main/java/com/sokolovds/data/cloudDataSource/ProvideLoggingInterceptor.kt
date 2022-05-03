package com.sokolovds.data.cloudDataSource

import okhttp3.logging.HttpLoggingInterceptor

interface ProvideLoggingInterceptor {
    fun interceptor(): HttpLoggingInterceptor

    abstract class Abstract(
        private val loggingLevel: HttpLoggingInterceptor.Level
    ) : ProvideLoggingInterceptor {
        override fun interceptor(): HttpLoggingInterceptor {
            val logger = HttpLoggingInterceptor()
            logger.level = loggingLevel
            return logger
        }
    }

    class Base : Abstract(HttpLoggingInterceptor.Level.BODY)

}