package com.sokolovds.githubusers.di

import androidx.paging.PagingConfig
import com.sokolovds.domain.DefaultValues
import com.sokolovds.githubusers.presentation.screens.mainScreen.MainFragmentViewModel
import com.sokolovds.githubusers.presentation.screens.profileScreen.ProfileFragmentViewModel
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainFragmentViewModel> {
        MainFragmentViewModel(
            getUsersPagingSource = get(),
            setCurrentUser = get()
        )
    }

    viewModel<ProfileFragmentViewModel> {
        ProfileFragmentViewModel(
            getCurrentUserAsFlow = get(),
            getUserByLogin = get()
        )
    }

    factory<UiErrorHandler> {
        UiErrorHandler.Base(context = get())
    }

    factory<PagingConfig> { PagingConfig(pageSize = DefaultValues.PAGE_SIZE) }
}