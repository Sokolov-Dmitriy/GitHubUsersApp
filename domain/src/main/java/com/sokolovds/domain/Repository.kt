package com.sokolovds.domain

import androidx.paging.PagingSource

import com.sokolovds.domain.models.User
import com.sokolovds.domain.models.Result
import com.sokolovds.domain.models.UserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


interface Repository {
    val currentUserLogin: Flow<String>
    fun getUsersPagingSource(searchBy: String): PagingSource<Int, UserItem>
    fun getUserByLogin(login: String): Flow<Result<User>>
    fun setCurrentLogin(login: String)

    abstract class Abstract() : Repository {
        protected open val pageSize = DefaultValues.PAGE_SIZE
        protected val _currentUserLogin = MutableStateFlow("-1")
        override val currentUserLogin: Flow<String> = _currentUserLogin
    }

}