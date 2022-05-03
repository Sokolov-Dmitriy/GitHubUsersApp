package com.sokolovds.data.cloudDataSource.models.singleUser

import com.sokolovds.domain.models.User

data class GetUserByLoginResponse(
    val avatar_url: String,
    val blog: String?,
    val company: String?,
    val created_at: String,
    val email: String?,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String?,
    val login: String,
    val name: String?,

) {
    fun toUser(): User {
        return User(
            name = name,
            avatarUrl = avatar_url,
            company = company,
            email = email,
            createdAt = created_at,
            followersCount = followers,
            followingCount = following,
            location = location,
            website = if (blog != null && blog.isNotEmpty()) blog else null,
            login = login
        )
    }
}
