package com.sokolovds.githubusers.presentation.screens.mainScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sokolovds.domain.DefaultValues
import com.sokolovds.domain.models.UserItem
import com.sokolovds.domain.usecase.GetUsersPagingSource
import com.sokolovds.domain.usecase.SetCurrentUser
import com.sokolovds.githubusers.presentation.adapters.UserAdapter
import com.sokolovds.githubusers.presentation.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MainFragmentViewModel(
    private val getUsersPagingSource: GetUsersPagingSource,
    private val setCurrentUser: SetCurrentUser
) : BaseViewModel(), UserAdapter.ClickListener, KoinComponent {
    val flow: Flow<PagingData<UserItem>>

    private val searchBy = MutableStateFlow("")
    private val pagingConfig by inject<PagingConfig>()

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

    private fun setupUsersPager(searchBy: String): Flow<PagingData<UserItem>> {
        return Pager(pagingConfig) {
            getUsersPagingSource(searchBy)
        }.flow
    }

    override fun onItemClick(login: String) {
        setCurrentUser(login)
        navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
    }

}