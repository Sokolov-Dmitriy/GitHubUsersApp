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
        //add your token in local.properties like: token = ghp_dds..ds (without quotation marks)
        private val token: String = BuildConfig.TOKEN,
        val value: String = if (token != "null") "$type $token" else ""
    )

    val HEADER = Header()

    //for getUsers request
    fun getFinishedQuery(query: String) = "$query in:login&type:user"
}