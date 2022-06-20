package com.sokolovds.githubusers.presentation.utils.navigation

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.sokolovds.githubusers.presentation.utils.ViewHandler
import kotlinx.coroutines.flow.SharedFlow

abstract class NavigationHandler(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val navActionFlow: SharedFlow<NavigationAction>,
    private val navController: NavController
) : ViewHandler {

    private var isSubscribe = false

    override fun subscribe() {
        if (!isSubscribe) {
            lifecycleScope.launchWhenCreated {
                navActionFlow.collect { action ->
                    handleNavAction(action)
                }
            }
            isSubscribe = true
        }
    }

    private fun handleNavAction(action: NavigationAction) {
        when (action) {
            is NavigationAction.ToDirection -> {
                navController.navigate(action.directions)
            }
            is NavigationAction.Back -> {
                navController.popBackStack()
            }
        }
    }

    class Base(
        lifecycleScope: LifecycleCoroutineScope,
        navActionFlow: SharedFlow<NavigationAction>,
        navController: NavController
    ) : NavigationHandler(lifecycleScope, navActionFlow, navController)

}