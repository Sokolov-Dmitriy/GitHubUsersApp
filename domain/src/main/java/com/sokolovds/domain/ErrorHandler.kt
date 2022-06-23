package com.sokolovds.domain

import com.sokolovds.domain.utils.ApiError

interface ErrorHandler {
    fun parseError(e: Throwable): ApiError
    fun parseError(code: Int): ApiError
}