package com.sokolovds.data.cloudDataSource.models.singleUser

import android.annotation.SuppressLint
import com.sokolovds.domain.models.User
import java.text.SimpleDateFormat

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

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
        val newFormat = SimpleDateFormat("yyyy-MM-dd")
        return newFormat.format(oldFormat.parse(date)!!)
    }

    fun toUser() = User(
        name = name,
        avatarUrl = avatar_url,
        company = company,
        email = email,
        createdAt = parseDate(created_at),
        followersCount = followers,
        followingCount = following,
        location = location,
        website = if (blog != null && blog.isNotEmpty()) blog else null,
        login = login
    )

}
