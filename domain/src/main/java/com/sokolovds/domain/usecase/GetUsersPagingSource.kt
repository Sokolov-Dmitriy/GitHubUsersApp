package com.sokolovds.domain.usecase

import androidx.paging.PagingSource
import com.sokolovds.domain.Repository
import com.sokolovds.domain.models.UserItem

class GetUsersPagingSource(private val repository: Repository) {
    operator fun invoke(searchBy:String): PagingSource<Int, UserItem> {
        return repository.getUsersPagingSource(searchBy)
    }
}