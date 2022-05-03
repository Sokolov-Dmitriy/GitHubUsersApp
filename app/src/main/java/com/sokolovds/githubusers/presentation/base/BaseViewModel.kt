package com.sokolovds.githubusers.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.sokolovds.githubusers.presentation.navigation.Event
import com.sokolovds.githubusers.presentation.navigation.NavigationAction

open class BaseViewModel:ViewModel() {
    private val _navigation = MutableLiveData<Event<NavigationAction>>()
    val navigation: LiveData<Event<NavigationAction>> get() = _navigation

    fun navigate(navDirections: NavDirections) {
        _navigation.value = Event(NavigationAction.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationAction.Back)
    }
}