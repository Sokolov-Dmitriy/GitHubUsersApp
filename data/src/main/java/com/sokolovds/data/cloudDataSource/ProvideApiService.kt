package com.sokolovds.data.cloudDataSource

import com.sokolovds.domain.DefaultValues

interface ProvideApiService {
    fun <T> service(clazz: Class<T>): T

    abstract class Abstract(
        private val retrofitBuilder: ProvideRetrofitBuilder,
    ) : ProvideApiService {

        private val retrofit by lazy {
            retrofitBuilder.provideRetrofitBuilder()
                .baseUrl(baseUrl())
                .build()
        }

        override fun <T> service(clazz: Class<T>): T = retrofit.create(clazz)

        protected open fun baseUrl(): String = DefaultValues.BASE_URL
    }

    class Base(retrofitBuilder: ProvideRetrofitBuilder) : Abstract(retrofitBuilder)
}