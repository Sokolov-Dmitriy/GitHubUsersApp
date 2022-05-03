package com.sokolovds.data

import androidx.paging.PagingSource
import com.sokolovds.data.cloudDataSource.UsersApi
import com.sokolovds.domain.ErrorHandler
import com.sokolovds.domain.Repository
import com.sokolovds.domain.models.Result
import com.sokolovds.domain.models.User
import com.sokolovds.domain.models.UserItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class RepositoryImp(
    private val service: UsersApi,
    private val errorHandler: ErrorHandler,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository.Abstract() {

    override val currentUserLogin: Flow<String> = _currentUserLogin

    override fun setCurrentLogin(login: String) {
        _currentUserLogin.value = login
    }

    override fun getUsersPagingSource(searchBy: String): PagingSource<Int, UserItem> {
        return UsersPagingSource(service, pageSize, searchBy, errorHandler)
    }

    override fun getUserByLogin(login: String): Flow<Result<User>> {
        return flow {
            val response = service.getUserByLogin(login)
            emit(
                if (response.isSuccessful) {
                    val user = response.body()!!.toUser()
                    Result.Success(user)
                } else Result.Error(errorHandler.parseError(response.code()))
            )
        }
            .flowOn(dispatcher)
            .catch { emit(Result.Error(errorHandler.parseError(it))) }
            .onStart { emit(Result.Loading) }
    }
}

