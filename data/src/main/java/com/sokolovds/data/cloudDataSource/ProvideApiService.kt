package com.sokolovds.data.cloudDataSource

import com.sokolovds.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal interface ProvideApiService {
    fun <T> service(clazz: Class<T>): T

    abstract class Abstract(
        private val baseUrl: String,
        private val headers: Interceptor,
        private val loggingLevel: HttpLoggingInterceptor.Level,
        private val converterFactory: Converter.Factory
    ) : ProvideApiService {

        private fun getLoggingInterceptor() = HttpLoggingInterceptor().setLevel(loggingLevel)

        private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(getLoggingInterceptor())
            }
            builder.addInterceptor(headers)
            return builder
        }

        private fun getRetrofitBuilder() = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(getOkHttpClientBuilder().build())

        override fun <T> service(clazz: Class<T>): T = getRetrofitBuilder()
            .baseUrl(baseUrl)
            .build()
            .create(clazz)
    }

    class Base(
        baseUrl: String,
        headers: Interceptor = Headers(),
        loggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
        converterFactory: Converter.Factory = GsonConverterFactory.create()
    ) : Abstract(baseUrl, headers, loggingLevel, converterFactory)
}