package com.sokolovds.data.di

import com.sokolovds.data.repositories.RepositoryImp
import com.sokolovds.data.utils.ErrorHandler
import com.sokolovds.domain.Repository
import org.koin.dsl.module

val repositoriesModule = module {
    single<Repository> {
        RepositoryImp(
            service = get(),
            errorHandler = get()
        )
    }

    factory<ErrorHandler> {
        ErrorHandler.Base()
    }
}
