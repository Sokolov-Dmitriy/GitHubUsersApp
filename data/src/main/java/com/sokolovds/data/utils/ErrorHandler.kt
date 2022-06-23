package com.sokolovds.data.utils

import com.sokolovds.domain.utils.ApiCode
import com.sokolovds.domain.utils.ApiError
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
            is HttpException -> {
                println("DEBUG:EXCEPTION:HttpException")
                ApiError.Network
            }
            is java.net.SocketTimeoutException -> {
                println("DEBUG:EXCEPTION:SocketTimeoutException")
                ApiError.Network
            }
            is java.net.UnknownHostException -> {
                println("DEBUG:EXCEPTION:UnknownHostException")
                ApiError.Network
            }
            is java.io.InterruptedIOException->{
                println("DEBUG:EXCEPTION:InterruptedIOException")
                ApiError.Network
            }
            is java.io.IOException->{
                println("DEBUG:EXCEPTION:IOException")
                ApiError.Network
            }
            else -> {
                println("DEBUG:EXCEPTION:Else:${e}")
                ApiError.Unknown
            }
        }
    }

    class Base : Abstract()
}