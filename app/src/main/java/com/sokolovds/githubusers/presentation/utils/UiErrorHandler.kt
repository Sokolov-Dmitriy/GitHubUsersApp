package com.sokolovds.githubusers.presentation.utils

import android.content.Context
import com.sokolovds.domain.ApiError
import com.sokolovds.githubusers.R


interface UiErrorHandler {

    fun getString(error: Throwable): String

    class Base(private val context: Context) : UiErrorHandler {
        override fun getString(error: Throwable) = when (error) {
            is ApiError.Network -> {
                context.getString(R.string.problems_with_internet_connection)
            }

            is ApiError.EmptyResponse -> {
                context.getString(R.string.user_with_such_nickname_could_not_be_found)
            }

            is ApiError.QueryWithOutArgs -> {
                context.getString(R.string.enter_the_github_nickname)
            }

            is ApiError.ValidationFailed -> {
                context.getString(R.string.you_are_using_invalid_characters)
            }

            is ApiError.Forbidden -> {
                context.getString(R.string.you_have_reached_the_request_limit_please_wait_a_bit)
            }

            is ApiError.ServiceUnavailable -> {
                context.getString(R.string.some_problems_on_the_server)
            }

            is ApiError.Unknown -> {
                context.getString(R.string.problems_with_internet_connection)
            }
            else -> context.getString(R.string.problems_with_internet_connection)
        }
    }
}
