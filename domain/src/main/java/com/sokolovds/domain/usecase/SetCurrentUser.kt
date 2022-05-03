package com.sokolovds.domain.usecase

import com.sokolovds.domain.Repository
class SetCurrentUser(private val repository: Repository) {
    operator fun invoke(login: String) {
        repository.setCurrentLogin(login)
    }
}