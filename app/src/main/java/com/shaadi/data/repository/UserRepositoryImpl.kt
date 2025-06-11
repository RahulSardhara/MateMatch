package com.shaadi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.shaadi.data.local.db.AppDatabase
import com.shaadi.data.local.entity.UserEntity
import com.shaadi.data.remote.api.NetworkService
import com.shaadi.data.remote.mapper.toDomain
import com.shaadi.domain.model.UserData
import com.shaadi.domain.repository.UserRepository
import com.shaadi.utils.Constant
import com.shaadi.utils.Constant.UserListType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val db: AppDatabase
) : UserRepository {

    override fun getUsers(status: String): Flow<PagingData<UserData>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UserRemoteMediator(networkService, db),
            pagingSourceFactory = { db.userDao().getAll(status) }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun updateUserStatus(id: String, status: String): Int {
        val user = db.userDao().getUsersByStatus(listOf(UserListType.UNKNOWN.toString())).firstOrNull { it.id == id } ?: return 0
        return db.userDao().update(user.copy(status = status))
    }

    override suspend fun getUserListByStatus(status: String): List<UserEntity> {
        val user = db.userDao().getUsersByStatus(listOf(status))
        return user
    }

}
