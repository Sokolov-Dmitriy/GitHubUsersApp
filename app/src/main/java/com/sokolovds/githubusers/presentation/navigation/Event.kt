package com.sokolovds.githubusers.presentation.navigation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(
        owner
    ) {
        it?.let(observer)
    }
}