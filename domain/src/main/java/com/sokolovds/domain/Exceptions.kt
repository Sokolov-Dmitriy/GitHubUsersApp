package com.sokolovds.domain


sealed class ApiError : Exception() {

    object Network : ApiError()

    object EmptyResponse : ApiError()

    object QueryWithOutArgs : ApiError()

    object ValidationFailed : ApiError()

    object Forbidden : ApiError()

    object ServiceUnavailable : ApiError()

    object Unknown : ApiError()

    object ParseDataFailed : ApiError()

    object EmptyResponseBody : ApiError()
}

enum class ApiCode(val code: Int) {
    VALIDATION_FAILED(422),
    FORBIDDEN(403),
    SERVICE_UNAVAILABLE(503)
}

//304 - Not modified
//403 - Forbidden(лимит запросов)
//422 - Validation failed(пустой запрос)
//503 - Service unavailable