package com.sokolovds.githubusers.presentation.screens.profileScreen.entities

import com.sokolovds.domain.models.User
import com.sokolovds.githubusers.presentation.chekNull
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
        private const val DEFAULT_STRING_VALUE = ""
        fun fromDomainUserEntity(entity: User): ProfileFragmentUserEntity {
            return ProfileFragmentUserEntity(
                name = entity.name.chekNull(DEFAULT_STRING_VALUE),
                login = entity.login,
                email = entity.email.chekNull(DEFAULT_STRING_VALUE),
                company = entity.company.chekNull(DEFAULT_STRING_VALUE),
                createdAt = entity.createdAt,
                followersCount = entity.followersCount,
                followingCount = entity.followingCount,
                location = entity.location.chekNull(DEFAULT_STRING_VALUE),
                website = entity.website.chekNull(DEFAULT_STRING_VALUE),
                avatarUrl = entity.avatarUrl
            )
        }
    }
}

