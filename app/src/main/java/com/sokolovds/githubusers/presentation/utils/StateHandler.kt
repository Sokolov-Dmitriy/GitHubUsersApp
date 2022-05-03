package com.sokolovds.githubusers.presentation.utils

interface StateHandler {
    fun onErrorState()
    fun onLoadingState()
    fun onNotLoadingState()
    fun onSuccessState()
}