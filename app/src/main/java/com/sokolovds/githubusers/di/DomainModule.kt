package com.sokolovds.githubusers.di

import com.sokolovds.data.utils.ErrorHandler
import com.sokolovds.domain.usecase.GetUserByLogin
import com.sokolovds.domain.usecase.GetUsersPagingSource
import org.koin.dsl.module

val domainModule = module {
    factory<GetUsersPagingSource> {
        GetUsersPagingSource(
            repository = get()
        )
    }

    factory<GetUserByLogin> {
        GetUserByLogin(
            repository = get()
        )
    }
}