package com.sokolovds.githubusers.di

import com.sokolovds.domain.ErrorHandler
import com.sokolovds.domain.usecase.GetCurrentUserAsFlow
import com.sokolovds.domain.usecase.GetUserByLogin
import com.sokolovds.domain.usecase.GetUsersPagingSource
import com.sokolovds.domain.usecase.SetCurrentUser
import org.koin.dsl.module

val domainModule = module {
    factory<GetUsersPagingSource> {
        GetUsersPagingSource(
            repository = get()
        )
    }

    factory<GetCurrentUserAsFlow> {
        GetCurrentUserAsFlow(
            repository = get()
        )
    }

    factory<SetCurrentUser> {
        SetCurrentUser(
            repository = get()
        )
    }

    factory<GetUserByLogin> {
        GetUserByLogin(
            repository = get()
        )
    }

    factory<ErrorHandler> {
        ErrorHandler.Base()
    }
}