package com.sokolovds.domain

object DefaultValues {
    //for paging config
    const val PAGE_SIZE = 50

    //for retrofit
    const val BASE_URL = "https://api.github.com/"

    //for authHeader
    data class Header(
        val name: String = "Authorization",
        val type: String = "Bearer",
        //create your Personal access tokens in GitHub Settings->Developer settings->Personal access tokens
        //choose only:"read:user, user:email"
        val token: String = " ",
        val value: String = "$type $token"
    )

    val HEADER = Header()

    const val INPUT_DEBOUNCE = 500L

    //for getUsers request
    fun getFinishedQuery(query: String) = "$query in:Login"


}