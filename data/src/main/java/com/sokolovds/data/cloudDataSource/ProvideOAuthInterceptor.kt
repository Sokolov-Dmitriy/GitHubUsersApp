package com.sokolovds.data.cloudDataSource

import com.sokolovds.domain.DefaultValues
import okhttp3.Interceptor

interface ProvideOAuthInterceptor : Interceptor {
    abstract class Abstract() : ProvideOAuthInterceptor {

        protected open val header = DefaultValues.HEADER

        override fun intercept(chain: Interceptor.Chain) = chain.proceed(
            chain
                .request()
                .newBuilder()
                .header(header.name, header.value)
                .build()
        )
    }

    class Base:Abstract()
}
