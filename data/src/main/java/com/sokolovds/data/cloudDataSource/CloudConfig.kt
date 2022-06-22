package com.sokolovds.data.cloudDataSource

import com.sokolovds.data.BuildConfig

object CloudConfig {

    //for retrofit
    const val BASE_URL = "https://api.github.com/"

    //for authHeader
    data class Header(
        val name: String = "Authorization",
        private val type: String = "Bearer",
        //create your Personal access tokens in GitHub Settings->Developer settings->Personal access tokens
        //choose only:"read:user, user:email"
        private val token: String = BuildConfig.TOKEN,
        val value: String = "$type $token"
    )

    val HEADER = Header()

    //for getUsers request
    fun getFinishedQuery(query: String) = "$query in:login&type:user"
}