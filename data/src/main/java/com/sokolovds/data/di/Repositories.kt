package com.sokolovds.data.di

import com.sokolovds.data.repositories.RepositoryImp
import com.sokolovds.domain.Repository
import org.koin.dsl.module

val repositoriesModule = module {
    single<Repository> {
        RepositoryImp(
            service = get(),
            errorHandler = get()
        )
    }
}
