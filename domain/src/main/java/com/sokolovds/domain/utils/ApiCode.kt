package com.sokolovds.domain.utils

enum class ApiCode(val code: Int) {
    VALIDATION_FAILED(422),
    FORBIDDEN(403),
    SERVICE_UNAVAILABLE(503)
}