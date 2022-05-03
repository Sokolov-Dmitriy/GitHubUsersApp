package com.sokolovds.githubusers.presentation.screens.profileScreen

import androidx.lifecycle.viewModelScope
import com.sokolovds.domain.models.Result
import com.sokolovds.domain.models.User
import com.sokolovds.domain.usecase.GetCurrentUserAsFlow
import com.sokolovds.domain.usecase.GetUserByLogin
import com.sokolovds.githubusers.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    private val getCurrentUserAsFlow: GetCurrentUserAsFlow,
    private val getUserByLogin: GetUserByLogin
) : BaseViewModel() {


    private val _userData = MutableStateFlow<Result<User>>(Result.Loading)
    val userData = _userData.asStateFlow()


    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getCurrentUserAsFlow().collectLatest {
                getUserByLogin(it).collectLatest { result ->
                    _userData.emit(result)
                }
            }
        }
    }

    fun onTryAgainPressed() {
        loadData()
    }


}