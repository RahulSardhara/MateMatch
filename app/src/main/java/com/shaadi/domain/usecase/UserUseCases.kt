package com.shaadi.domain.usecase

import com.shaadi.data.remote.mapper.toDomain
import com.shaadi.data.remote.network.DataState
import com.shaadi.data.remote.network.UIText
import com.shaadi.domain.model.UserData
import com.shaadi.domain.repository.UserRepository
import com.shaadi.utils.Constant.UserListType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val fetchAllUserListUseCase: FetchAllUserListUseCase,
    val fetchRejectedUserListUseCase: FetchRejectedUserListUseCase,
    val fetchAcceptedUserListUseCase: FetchAcceptedUserListUseCase,
    val updateUserStatusUseCase: UpdateUserStatusUseCase
)


class FetchAllUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUsers(UserListType.UNKNOWN.toString())

}

class FetchRejectedUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataState<List<UserData>>> = flow {
        emit(DataState.Loading(true))
        try {
            val res = userRepository.getUserListByStatus(UserListType.REJECTED.toString())
            val mapped = res.map { it.toDomain() }
            emit(DataState.Success(mapped))
        } catch (e: Exception) {
            emit(DataState.ExceptionError(UIText.DynamicString(e.message.orEmpty()), e))
        } finally {
            emit(DataState.Loading(false))
        }
    }

}

class FetchAcceptedUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataState<List<UserData>>> = flow {
        emit(DataState.Loading(true))
        try {
            val res = userRepository.getUserListByStatus(UserListType.ACCEPTED.toString())
            val mapped = res.map { it.toDomain() }
            emit(DataState.Success(mapped))
        } catch (e: Exception) {
            emit(DataState.ExceptionError(UIText.DynamicString(e.message.orEmpty()), e))
        } finally {
            emit(DataState.Loading(false))
        }
    }

}


class UpdateUserStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String, status: String): Flow<DataState<Int>> = flow {
        emit(DataState.Loading(true))
        try {
            val result: Int = userRepository.updateUserStatus(id, status)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.ExceptionError(UIText.DynamicString(e.message.orEmpty()), e))
        } finally {
            emit(DataState.Loading(false))
        }
    }

}

