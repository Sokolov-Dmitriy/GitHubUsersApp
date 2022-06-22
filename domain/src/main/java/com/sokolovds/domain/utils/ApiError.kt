package com.sokolovds.domain.utils


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



//304 - Not modified
//403 - Forbidden(лимит запросов)
//422 - Validation failed(пустой запрос)
//503 - Service unavailable