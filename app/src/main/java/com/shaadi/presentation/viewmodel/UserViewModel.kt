package com.shaadi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shaadi.R
import com.shaadi.data.remote.network.DataState
import com.shaadi.data.remote.network.UIComponent
import com.shaadi.data.remote.network.UIText
import com.shaadi.data.remote.network.uiMessageChannel
import com.shaadi.data.remote.network.uiProgressChannel
import com.shaadi.domain.model.UserData
import com.shaadi.domain.usecase.UserUseCases
import com.shaadi.utils.Constant.UserListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCases
) : ViewModel() {

    val allUsersList: Flow<PagingData<UserData>> = userUseCase.fetchAllUserListUseCase()
        .cachedIn(viewModelScope)

    private val _currentUserAcceptAndRejectListType = MutableStateFlow<PagingData<UserData>>(PagingData.empty())
    val currentUserListType: StateFlow<PagingData<UserData>> = _currentUserAcceptAndRejectListType.asStateFlow()


    fun getAcceptRejectedUserList(isAccepted: Boolean = true) {
        if (!isAccepted) {
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.fetchRejectedUserListUseCase().collectLatest { state ->
                    when (state) {
                        is DataState.Loading -> uiProgressChannel.send(Pair(state.isLoading, false))
                        is DataState.Success -> {
                            Timber.d("Success---> ${state.data}")
                            val userList = (state.data as? List<UserData>).orEmpty()
                            if (userList.isEmpty()) {
                                uiProgressChannel.send(Pair(false, true))
                                _currentUserAcceptAndRejectListType.value = PagingData.from(emptyList())
                            } else {
                                uiProgressChannel.send(Pair(false, false))
                                _currentUserAcceptAndRejectListType.value = PagingData.from(state.data as List<UserData>)
                            }
                        }

                        is DataState.ExceptionError -> {
                            uiProgressChannel.send(Pair(false, false))
                            uiMessageChannel.send(UIComponent.SnackBar(state.message))
                        }

                        is DataState.ResponseError -> {
                            uiProgressChannel.send(Pair(false, false))
                            uiMessageChannel.send(UIComponent.SnackBar(state.message))
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.fetchAcceptedUserListUseCase().collectLatest { state ->
                    when (state) {
                        is DataState.Loading -> uiProgressChannel.send(Pair(state.isLoading, false))
                        is DataState.Success -> {
                            Timber.d("Success---> ${state.data}")
                            val userList = (state.data as? List<UserData>).orEmpty()
                            if (userList.isEmpty()) {
                                uiProgressChannel.send(Pair(false, true))
                                _currentUserAcceptAndRejectListType.value = PagingData.from(emptyList())
                            } else {
                                uiProgressChannel.send(Pair(false, false))
                                _currentUserAcceptAndRejectListType.value = PagingData.from(state.data as List<UserData>)
                            }

                        }

                        is DataState.ExceptionError -> {
                            uiProgressChannel.send(Pair(false, false))
                            uiMessageChannel.send(UIComponent.SnackBar(state.message))
                        }

                        is DataState.ResponseError -> {
                            uiProgressChannel.send(Pair(false, false))
                            uiMessageChannel.send(UIComponent.SnackBar(state.message))
                        }
                    }
                }
            }
        }


    }

    fun updateUserStatus(userData: UserData?) {
        userData?.let {
            userUseCase.updateUserStatusUseCase(it.login?.uuid ?: "", it.status ?: UserListType.UNKNOWN.toString()).onEach { state ->
                when (state) {
                    is DataState.Loading -> uiProgressChannel.send(Pair(state.isLoading, false))
                    is DataState.Success -> {
                        uiProgressChannel.send(Pair(false, false))
                        if (state.data > 0) {
                            uiMessageChannel.send(UIComponent.Toast(UIText.StringResource(R.string.status_updated_successfully)))
                        } else {
                            uiMessageChannel.send(UIComponent.SnackBar(UIText.StringResource(R.string.something_went_wrong)))
                        }

                    }

                    is DataState.ExceptionError -> {
                        uiProgressChannel.send(Pair(false, false))
                        uiMessageChannel.send(UIComponent.SnackBar(state.message))
                    }

                    is DataState.ResponseError -> {
                        uiProgressChannel.send(Pair(false, false))
                        uiMessageChannel.send(UIComponent.SnackBar(state.message))
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}