package com.sokolovds.domain

object DefaultValues {
    //for paging config
    const val PAGE_SIZE = 50

    //for retrofit
    const val BASE_URL = "https://api.github.com/"

    //for authHeader
    data class Header(
        val name: String = "Authorization",
        val value: String = "Bearer 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"
    )

    val HEADER = Header()

    const val INPUT_DEBOUNCE = 500L

    //for getUsers request
    fun getFinishedQuery(query: String) = "$query in:Login"


}