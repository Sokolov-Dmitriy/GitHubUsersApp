package com.sokolovds.githubusers.presentation.utils.stateHandler

import com.sokolovds.domain.utils.ApiError
import com.sokolovds.githubusers.presentation.utils.UIEntity


sealed class State<out T> {
    data class Success<out R: UIEntity>(val data: R) : State<R>()
    object Loading : State<Nothing>()
    data class Error(val error: ApiError) : State<Nothing>()
    object StartingConfiguration : State<Nothing>()
}