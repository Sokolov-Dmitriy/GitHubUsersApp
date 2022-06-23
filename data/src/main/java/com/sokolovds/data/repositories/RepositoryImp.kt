package com.sokolovds.data.repositories

import androidx.paging.PagingSource
import com.sokolovds.data.cloudDataSource.UsersApi
import com.sokolovds.domain.DefaultValues
import com.sokolovds.domain.ErrorHandler
import com.sokolovds.domain.Repository
import com.sokolovds.domain.models.Result
import com.sokolovds.domain.models.User
import com.sokolovds.domain.models.UserItem
import com.sokolovds.domain.utils.ApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RepositoryImp(
    private val service: UsersApi,
    private val errorHandler: ErrorHandler,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val pageSize: Int = DefaultValues.PAGE_SIZE
) : Repository {

    override fun getUsersPagingSource(searchBy: String): PagingSource<Int, UserItem> {
        return UsersPagingSource(service, pageSize, searchBy, errorHandler)
    }

    override fun getUserByLogin(login: String): Flow<Result<User>> {
        return flow {
            val response = service.getUserByLogin(login)
            emit(
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null) Result.Error(ApiError.EmptyResponseBody)
                    else {
                        val user = body.toUser()
                        Result.Success(user)
                    }
                } else Result.Error(errorHandler.parseError(response.code()))
            )
        }
            .flowOn(dispatcher)
            .catch { emit(Result.Error(errorHandler.parseError(it))) }
            .onStart { emit(Result.Loading) }
    }
}

