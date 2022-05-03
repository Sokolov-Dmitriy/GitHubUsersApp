package com.sokolovds.domain.usecase

import com.sokolovds.domain.Repository
import com.sokolovds.domain.models.Result
import com.sokolovds.domain.models.User
import kotlinx.coroutines.flow.Flow

class GetUserByLogin(private val repository: Repository) {
    operator fun invoke(login: String): Flow<Result<User>> {
        return repository.getUserByLogin(login)
    }
}