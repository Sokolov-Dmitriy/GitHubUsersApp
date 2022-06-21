package com.sokolovds.githubusers.presentation.screens.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokolovds.domain.models.*
import com.sokolovds.domain.usecase.GetUserByLogin
import com.sokolovds.githubusers.presentation.screens.profileScreen.entities.ProfileFragmentUserEntity
import com.sokolovds.githubusers.presentation.utils.stateHandler.StateController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    private val getUserByLogin: GetUserByLogin
) : ViewModel() {

    private val userController: StateController<ProfileFragmentUserEntity> =
        StateController.StateControllerImpl()
    val userState = userController.stateFlow

    lateinit var userLogin: String
    fun loadUserData(login: String) {
        userLogin = login
        viewModelScope.launch {
            getUserByLogin(login).collectLatest { result ->
                result
                    .onSuccess {
                        userController.successState(
                            ProfileFragmentUserEntity.fromDomainUserEntity(it)
                        )
                    }
                    .onLoading { userController.loadingState() }
                    .onError { userController.errorState(it) }
            }
        }
    }

    fun onTryAgainPressed() {
        if (this::userLogin.isInitialized) {
            loadUserData(userLogin)
        }
    }

}