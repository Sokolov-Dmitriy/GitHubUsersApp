package com.sokolovds.githubusers.presentation.screens.mainScreen.entities

import com.sokolovds.domain.models.UserItem
import com.sokolovds.githubusers.presentation.utils.UIEntity

data class MainFragmentUserItemEntity(
    val login: String,
    val avatarUrl: String
) : UIEntity {
    companion object {
        fun fromDomainUserItemEntity(entity: UserItem): MainFragmentUserItemEntity {
            return MainFragmentUserItemEntity(
                login = entity.login,
                avatarUrl = entity.avatarUrl
            )
        }
    }
}
