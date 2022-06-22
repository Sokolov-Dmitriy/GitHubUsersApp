package com.sokolovds.githubusers.di

import androidx.paging.PagingConfig
import com.sokolovds.domain.DefaultValues
import com.sokolovds.githubusers.presentation.screens.mainScreen.MainFragmentViewModel
import com.sokolovds.githubusers.presentation.screens.profileScreen.ProfileFragmentViewModel
import com.sokolovds.githubusers.presentation.screens.profileScreen.entities.ProfileFragmentUserEntity
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler
import com.sokolovds.githubusers.presentation.utils.ViewHandler
import com.sokolovds.githubusers.presentation.utils.navigation.NavigationController
import com.sokolovds.githubusers.presentation.utils.navigation.NavigationHandler
import com.sokolovds.githubusers.presentation.utils.stateHandler.StateHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel<MainFragmentViewModel> {
        MainFragmentViewModel(
            getUsersPagingSource = get(),
            navigationController = get(),
            pagingConfig = get()
        )
    }

    viewModel<ProfileFragmentViewModel> {
        ProfileFragmentViewModel(
            getUserByLogin = get()
        )
    }

    factory<UiErrorHandler> {
        UiErrorHandler.Base(context = get())
    }

    factory<PagingConfig> { PagingConfig(pageSize = DefaultValues.PAGE_SIZE) }

    factory<NavigationController> {
        NavigationController.NavigationControllerImpl()
    }

    factory<ViewHandler>(named(ViewHandlerEnum.NAVIGATION)) { args ->
        NavigationHandler.Base(
            lifecycleScope = args.get(),
            navActionFlow = args.get(),
            navController = args.get()
        )
    }

    factory<ViewHandler>(named(ViewHandlerEnum.USER_PROFILE)) { args ->
        StateHandler.Base<ProfileFragmentUserEntity>(
            lifecycleScope = args.get(),
            stateFlow = args.get(),
            handler = args.get()
        )
    }
}