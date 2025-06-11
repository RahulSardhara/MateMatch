package com.shaadi.data.remote.api

import com.shaadi.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("api/")
    suspend fun getUsers(@Query("results") count: Int = 10): Response<UserResponse>

}