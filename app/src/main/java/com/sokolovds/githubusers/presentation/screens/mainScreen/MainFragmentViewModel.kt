package com.sokolovds.githubusers.presentation.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sokolovds.domain.DefaultValues
import com.sokolovds.domain.models.UserItem
import com.sokolovds.domain.usecase.GetUsersPagingSource
import com.sokolovds.githubusers.presentation.adapters.UserAdapter
import com.sokolovds.githubusers.presentation.screens.mainScreen.entities.MainFragmentUserItemEntity
import com.sokolovds.githubusers.presentation.utils.navigation.NavigationController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
@kotlinx.coroutines.FlowPreview
class MainFragmentViewModel(
    private val navigationController: NavigationController,
    private val getUsersPagingSource: GetUsersPagingSource,
    private val pagingConfig: PagingConfig
) : ViewModel(){
    val flow: Flow<PagingData<MainFragmentUserItemEntity>>
    private val searchBy = MutableStateFlow("")
    val navActionFlow = navigationController.navActionFlow(viewModelScope)

    init {
        flow = searchBy
            .debounce(DefaultValues.INPUT_DEBOUNCE)
            .flatMapLatest { searchBy ->
                setupUsersPager(searchBy)
            }.cachedIn(viewModelScope)
    }

    fun searchUser(searchText: String) {
        if (searchText != searchBy.value) {
            searchBy.value = searchText
        }
    }

    private fun setupUsersPager(searchBy: String): Flow<PagingData<MainFragmentUserItemEntity>> {
        return Pager(pagingConfig) {
            getUsersPagingSource(searchBy)
        }.flow.map { pagingData->
            pagingData.map { MainFragmentUserItemEntity.fromDomainUserItemEntity(it) }
        }
    }

    fun onItemClick(login: String) {
        navigationController.navigateTo(
            MainFragmentDirections.actionMainFragmentToProfileFragment(login)
        )
    }

}