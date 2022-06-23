package com.sokolovds.data.utils

import com.sokolovds.domain.ErrorHandler
import com.sokolovds.domain.utils.ApiCode
import com.sokolovds.domain.utils.ApiError
import retrofit2.HttpException

class ErrorHandlerImpl() : ErrorHandler {
    override fun parseError(code: Int) = when (code) {
        ApiCode.FORBIDDEN.code -> ApiError.Forbidden
        ApiCode.VALIDATION_FAILED.code -> ApiError.ValidationFailed
        ApiCode.SERVICE_UNAVAILABLE.code -> ApiError.ServiceUnavailable
        else -> ApiError.Unknown
    }

    override fun parseError(e: Throwable) = when (e) {
        is HttpException -> {
            ApiError.Network
        }
        is java.net.SocketTimeoutException -> {
            ApiError.Network
        }
        is java.net.UnknownHostException -> {
            ApiError.Network
        }
        is java.io.InterruptedIOException -> {
            ApiError.Network
        }
        is java.io.IOException -> {
            ApiError.Network
        }
        else -> {
            ApiError.Unknown
        }
    }
}

