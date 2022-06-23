package com.sokolovds.githubusers.presentation.utils.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface NavigationController {
    fun navigateTo(directions: NavDirections)
    fun back()
    fun navActionFlow(scope: CoroutineScope): SharedFlow<NavigationAction>

    class NavigationControllerImpl : NavigationController {

        private var _coroutineScope: CoroutineScope? = null
        private val _navActionFlow = MutableSharedFlow<NavigationAction>(0,0)

        override fun navigateTo(directions: NavDirections) = launch {
            _navActionFlow.emit(NavigationAction.ToDirection(directions))
        }

        override fun back() = launch {
            _navActionFlow.emit(NavigationAction.Back)
        }

        private fun launch(block: suspend CoroutineScope.() -> Unit) {
            _coroutineScope?.launch { block() }
        }

        override fun navActionFlow(scope: CoroutineScope) = _navActionFlow.asSharedFlow().apply {
            _coroutineScope = scope
        }
    }
}


