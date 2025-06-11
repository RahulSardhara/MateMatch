package com.shaadi.domain.repository

import androidx.paging.PagingData
import com.shaadi.data.local.entity.UserEntity
import com.shaadi.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // Fetches a list of users
    fun getUsers(status: String): Flow<PagingData<UserData>>
    suspend fun updateUserStatus(id: String, status: String): Int
    suspend fun getUserListByStatus(status: String): List<UserEntity>
}