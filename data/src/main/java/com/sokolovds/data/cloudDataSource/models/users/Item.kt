package com.sokolovds.data.cloudDataSource.models.users

import com.sokolovds.domain.models.UserItem

data class Item(
    val avatar_url: String,
    val id: Int,
    val login: String,
) {
    fun toUserItem() = UserItem(
        id = id.toLong(),
        login = login,
        avatarUrl = avatar_url
    )
}
