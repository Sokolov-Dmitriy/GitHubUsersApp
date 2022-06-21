package com.sokolovds.githubusers.presentation.utils.stateHandler

import com.sokolovds.domain.ApiError
import com.sokolovds.githubusers.presentation.utils.UIEntity
import kotlinx.coroutines.flow.*

interface StateController<UI : UIEntity> {
    fun successState(data: UI)
    fun loadingState()
    fun startingState()
    fun errorState(error: ApiError)
    val stateFlow: StateFlow<State<UI>>

    class StateControllerImpl<UI : UIEntity> : StateController<UI> {
        override val stateFlow
            get() = _stateFlow.asStateFlow()
        private val _stateFlow = MutableStateFlow<State<UI>>(State.StartingConfiguration)

        override fun successState(data: UI) {
            _stateFlow.value = State.Success(data)
        }

        override fun loadingState() {
            _stateFlow.value = State.Loading
        }

        override fun startingState() {
            _stateFlow.value = State.StartingConfiguration
        }

        override fun errorState(error: ApiError) {
            _stateFlow.value = State.Error(error)
        }
    }
}

