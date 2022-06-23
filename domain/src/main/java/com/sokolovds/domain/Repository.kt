package com.sokolovds.domain

import androidx.paging.PagingSource

import com.sokolovds.domain.models.User
import com.sokolovds.domain.models.Result
import com.sokolovds.domain.models.UserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


interface Repository {
    fun getUsersPagingSource(searchBy: String): PagingSource<Int, UserItem>
    fun getUserByLogin(login: String): Flow<Result<User>>
}