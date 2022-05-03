package com.sokolovds.domain.usecase

import com.sokolovds.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserAsFlow(private val repository: Repository) {
    operator fun invoke(): Flow<String> {
        return repository.currentUserLogin
    }
}