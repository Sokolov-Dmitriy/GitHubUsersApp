package com.sokolovds.data.cloudDataSource.models.users

import com.sokolovds.domain.models.UserItem

data class GetUsersResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) {
    fun toUserItems(): List<UserItem> = items.map { it.toUserItem() }
}