package com.sokolovds.domain.models

import androidx.paging.LoadState
import com.sokolovds.domain.utils.ApiError

sealed class Result<out T> {
    companion object {
        const val ON_SUCCESS_DEFAULT = 1
    }

    data class Success<out R>(
        val value: R
    ) : Result<R>()

    data class Error(
        val exception: ApiError
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
}


inline fun <reified T> Result<T>.onError(block: (exception: ApiError) -> Unit): Result<T> {
    if (this is Result.Error) {
        block(exception)
    }
    return this
}

inline fun <reified T> Result<T>.onSuccess(block: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        block(value)
    }
    return this
}

inline fun <reified T> Result<T>.onLoading(block: () -> Unit): Result<T> {
    if (this is Result.Loading) {
        block()
    }
    return this
}

inline fun LoadState.onLoading(block: () -> Unit): LoadState {
    if (this is LoadState.Loading) {
        block()
    }
    return this
}

inline fun LoadState.onError(block: (error: Throwable) -> Unit): LoadState {
    if (this is LoadState.Error) {
        block(error)
    }
    return this
}

inline fun LoadState.onNotLoading(block: () -> Unit): LoadState {
    if (this is LoadState.NotLoading) {
        block()
    }
    return this
}