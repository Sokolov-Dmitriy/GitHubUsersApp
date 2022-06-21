package com.sokolovds.githubusers.presentation.utils.stateHandler

import androidx.lifecycle.LifecycleCoroutineScope
import com.sokolovds.domain.ApiError
import com.sokolovds.githubusers.presentation.utils.UIEntity
import com.sokolovds.githubusers.presentation.utils.ViewHandler
import kotlinx.coroutines.flow.StateFlow


abstract class StateHandler<UI : UIEntity>(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val stateFlow: StateFlow<State<UI>>,
    private val handler: HandlerImplementation<UI>
) : ViewHandler {

    interface HandlerImplementation<UI : UIEntity> {
        fun onSuccessState(data: UI)
        fun onErrorState(error: ApiError)
        fun onLoadingState()
        fun setupStartConfiguration()
    }

    private var isSubscribe = false

    override fun subscribe() {
        lifecycleScope.launchWhenStarted {
            stateFlow.collect { event ->
                handleState(event)
            }
        }
    }

    private fun handleState(state: State<UI>) {
        when (state) {
            is State.Success<UI> -> {
                handler.onSuccessState(state.data)
            }
            is State.Loading -> {
                handler.onLoadingState()
            }
            is State.Error -> {
                handler.onErrorState(state.error)
            }
            is State.StartingConfiguration -> {
                handler.setupStartConfiguration()
            }
        }
    }

    class Base<UI : UIEntity>(
        lifecycleScope: LifecycleCoroutineScope,
        stateFlow: StateFlow<State<UI>>,
        handler: HandlerImplementation<UI>
    ) : StateHandler<UI>(lifecycleScope, stateFlow, handler)
}


