package com.sokolovds.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sokolovds.data.cloudDataSource.UsersApi
import com.sokolovds.domain.ApiError
import com.sokolovds.domain.DefaultValues
import com.sokolovds.domain.ErrorHandler
import com.sokolovds.domain.models.UserItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersPagingSource(
    private val service: UsersApi,
    private val pageSize: Int,
    private val query: String,
    private val errorHandler: ErrorHandler,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    PagingSource<Int, UserItem>() {

    private val finishedQuery = DefaultValues.getFinishedQuery(query)

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {
        return null
    }

    private suspend fun loadUsers(page: Int) = withContext(dispatcher) {
        service.getUsers(page, finishedQuery, pageSize)
    }

    private fun prevKey(currentPageNum: Int) =
        if (currentPageNum == 1) null else currentPageNum - 1

    private fun nextKey(currentPageNum: Int, totalCount: Int) =
        if (currentPageNum * pageSize >= totalCount) null else currentPageNum + 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        if (query.isEmpty()) return LoadResult.Error(ApiError.QueryWithOutArgs)
        try {
            val pageNumber = params.key ?: 1
            val response = loadUsers(pageNumber)
            if (response.isSuccessful) {
                val totalCount: Int = response.body()?.total_count!!
                val items = response.body()?.toUserItems()!!
                if (items.isEmpty()) return LoadResult.Error(ApiError.EmptyResponse)
                return LoadResult.Page(
                    data = items,
                    prevKey = prevKey(pageNumber),
                    nextKey = nextKey(pageNumber, totalCount)
                )
            } else return LoadResult.Error(errorHandler.parseError(response.code()))
        } catch (e: Exception) {
            return LoadResult.Error(errorHandler.parseError(e))
        }
    }
}