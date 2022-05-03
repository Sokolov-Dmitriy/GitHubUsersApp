package com.sokolovds.domain.models

data class User(
    val name: String?,
    val login: String,
    val email: String?,
    val company: String?,
    val createdAt: String,
    val followersCount: Int,
    val followingCount: Int,
    val location: String?,
    val website: String?,
    val avatarUrl:String


)
