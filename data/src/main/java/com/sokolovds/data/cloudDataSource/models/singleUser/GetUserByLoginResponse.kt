package com.sokolovds.data.cloudDataSource.models.singleUser

import android.annotation.SuppressLint
import com.sokolovds.domain.models.User
import com.sokolovds.domain.utils.ApiError
import com.sokolovds.domain.utils.chekNull
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
    val name: String?
) {
    fun toUser() = User(
        name = name.chekNull(DEFAULT_STRING_VALUE),
        avatarUrl = avatar_url,
        company = company.chekNull(DEFAULT_STRING_VALUE),
        email = email.chekNull(DEFAULT_STRING_VALUE),
        createdAt = parseDate(created_at),
        followersCount = followers,
        followingCount = following,
        location = location.chekNull(DEFAULT_STRING_VALUE),
        website = blog.chekNull(DEFAULT_STRING_VALUE),
        login = login
    )

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val oldFormat = SimpleDateFormat(OLD_FORMAT_DATE)
        val newFormat = SimpleDateFormat(NEW_FORMAT_DATE)
        return newFormat.format(oldFormat.parse(date) ?: throw ApiError.ParseDataFailed)
    }

    companion object {
        private const val OLD_FORMAT_DATE = "yyyy-MM-dd'T'hh:mm:ss'Z'"
        private const val NEW_FORMAT_DATE = "yyyy-MM-dd"
        private const val DEFAULT_STRING_VALUE = ""
    }
}
