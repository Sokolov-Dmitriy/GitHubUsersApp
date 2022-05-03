package com.sokolovds.domain

import retrofit2.HttpException

interface ErrorHandler {
    fun parseError(e: Throwable): ApiError
    fun parseError(code: Int): ApiError

    abstract class Abstract() : ErrorHandler {
        override fun parseError(code: Int) = when (code) {
            ApiCode.FORBIDDEN.code -> ApiError.Forbidden
            ApiCode.VALIDATION_FAILED.code -> ApiError.ValidationFailed
            ApiCode.SERVICE_UNAVAILABLE.code -> ApiError.ServiceUnavailable
            else -> ApiError.Unknown
        }

        override fun parseError(e: Throwable) = when (e) {
            is HttpException -> ApiError.Network
            else -> ApiError.Unknown
        }
    }

    class Base:Abstract()
}