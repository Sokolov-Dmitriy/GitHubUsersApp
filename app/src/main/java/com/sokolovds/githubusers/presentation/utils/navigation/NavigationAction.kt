package com.sokolovds.githubusers.presentation.utils.navigation

import androidx.navigation.NavDirections

sealed class NavigationAction {
    data class ToDirection(val directions: NavDirections) : NavigationAction()
    object Back : NavigationAction()
}