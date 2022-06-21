package com.sokolovds.data.di

import com.sokolovds.data.repositories.RepositoryImp
import com.sokolovds.data.cloudDataSource.*
import com.sokolovds.domain.DefaultValues
import com.sokolovds.domain.Repository
import org.koin.dsl.module


val cloudModule = module {
    factory<UsersApi> {
        ProvideApiService.Base(
            CloudConfig.BASE_URL,
            Headers().addHeader(CloudConfig.HEADER.name, CloudConfig.HEADER.value)
        ).service(UsersApi::class.java)
    }
}