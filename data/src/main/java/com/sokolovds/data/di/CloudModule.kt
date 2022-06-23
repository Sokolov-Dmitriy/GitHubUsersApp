package com.sokolovds.data.di

import com.sokolovds.data.cloudDataSource.CloudConfig
import com.sokolovds.data.cloudDataSource.Headers
import com.sokolovds.data.cloudDataSource.ProvideApiService
import com.sokolovds.data.cloudDataSource.UsersApi
import org.koin.dsl.module


val cloudModule = module {
    factory<UsersApi> {
        ProvideApiService.Base(
            CloudConfig.BASE_URL,
            Headers().addHeader(CloudConfig.HEADER.name, CloudConfig.HEADER.value)
        ).service(UsersApi::class.java)
    }
}