package com.sokolovds.githubusers.di

import com.sokolovds.data.RepositoryImp
import com.sokolovds.data.cloudDataSource.*
import com.sokolovds.domain.Repository
import org.koin.dsl.module

val dataModule = module {

    factory<ProvideConverterFactory> {
        ProvideConverterFactory.Base()
    }

    factory<ProvideLoggingInterceptor> {
        ProvideLoggingInterceptor.Base()
    }

    factory<ProvideOAuthInterceptor> {
        ProvideOAuthInterceptor.Base()
    }

    factory<ProvideOkHttpClientBuilder> {
        ProvideOkHttpClientBuilder.Base(
            provideLoggingInterceptor = get(),
            oAuthInterceptor = get()
        )
    }

    factory<ProvideRetrofitBuilder> {
        ProvideRetrofitBuilder.Base(
            provideConverterFactory = get(),
            httpClientBuilder = get()
        )
    }

    factory<UsersApi> {
        ProvideApiService.Base(
            retrofitBuilder = get()
        ).service(UsersApi::class.java)
    }

    single<Repository> {
        RepositoryImp(
            service = get(),
            errorHandler = get()
        )
    }
}