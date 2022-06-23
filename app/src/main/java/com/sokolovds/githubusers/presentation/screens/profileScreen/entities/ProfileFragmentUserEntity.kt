package com.sokolovds.githubusers.presentation.screens.profileScreen.entities

import com.sokolovds.domain.models.User
import com.sokolovds.githubusers.presentation.utils.UIEntity

data class ProfileFragmentUserEntity(
    val name: String,
    val login: String,
    val email: String,
    val company: String,
    val createdAt: String,
    val followersCount: Int,
    val followingCount: Int,
    val location: String,
    val website: String,
    val avatarUrl: String
) : UIEntity {
    companion object {
        fun fromDomainUserEntity(entity: User) = ProfileFragmentUserEntity(
            name = entity.name,
            login = entity.login,
            email = entity.email,
            company = entity.company,
            createdAt = entity.createdAt,
            followersCount = entity.followersCount,
            followingCount = entity.followingCount,
            location = entity.location,
            website = entity.website,
            avatarUrl = entity.avatarUrl
        )
    }
}

