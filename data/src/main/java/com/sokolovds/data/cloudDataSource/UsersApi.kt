package com.sokolovds.data.cloudDataSource

import com.sokolovds.data.cloudDataSource.models.singleUser.GetUserByLoginResponse
import com.sokolovds.data.cloudDataSource.models.users.GetUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {
    @GET("search/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("q") userName: String,
        @Query("per_page") perPage:Int
    ): Response<GetUsersResponse>

    @GET("users/{login}")
    suspend fun getUserByLogin(@Path("login") login:String):Response<GetUserByLoginResponse>
}


