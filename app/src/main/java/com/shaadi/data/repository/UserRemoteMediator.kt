package com.shaadi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shaadi.data.local.db.AppDatabase
import com.shaadi.data.local.entity.UserEntity
import com.shaadi.data.remote.api.NetworkService
import com.shaadi.data.remote.mapper.toUserEntity
import com.shaadi.utils.Constant
import com.shaadi.utils.Constant.UserListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val networkService: NetworkService,
    private val db: AppDatabase
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {
        val totalCount = state.pages.sumOf { it.data.size } + state.config.pageSize

        return try {
            val response = networkService.getUsers(count = totalCount)
            val userList = response.body()?.userData?.map { it.toUserEntity() } ?: emptyList()
            withContext(Dispatchers.IO) {
                db.withTransaction {
                    val existingUsers = db.userDao().getUsersByStatus(listOf(UserListType.ACCEPTED.toString(), UserListType.REJECTED.toString(), UserListType.UNKNOWN.toString()))
                    val existingUserMap = existingUsers.associateBy { it.id }

                    val updatedList = userList.map { newUser ->
                        val existing = existingUserMap[newUser.id]
                        val status = when (existing?.status) {
                            UserListType.ACCEPTED.toString() -> UserListType.ACCEPTED.toString()
                            UserListType.REJECTED.toString() -> UserListType.REJECTED.toString()
                            else -> UserListType.UNKNOWN.toString()
                        }
                        newUser.copy(status = status)
                    }

                    // Insert only new users
                    val newUsers = updatedList.filter { it.id !in existingUserMap.keys }
                    db.userDao().insertAll(newUsers)
                }
            }
            MediatorResult.Success(endOfPaginationReached = userList.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}