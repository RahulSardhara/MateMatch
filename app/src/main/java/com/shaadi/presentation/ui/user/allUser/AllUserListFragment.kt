package com.shaadi.presentation.ui.user.allUser

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.shaadi.R
import com.shaadi.core.CoreFragment
import com.shaadi.data.remote.network.UIComponent
import com.shaadi.data.remote.network.uiMessageEvents
import com.shaadi.data.remote.network.uiProgressEvents
import com.shaadi.databinding.FragmentAllUserListBinding
import com.shaadi.presentation.adapter.UserPagingAdapter
import com.shaadi.presentation.viewmodel.UserViewModel
import com.shaadi.utils.Constant
import com.shaadi.utils.setGone
import com.shaadi.utils.setVisible
import com.shaadi.utils.showSnackBar
import com.shaadi.utils.showToast
import com.shaadi.utils.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllUserListFragment : CoreFragment<FragmentAllUserListBinding>(layoutResId = R.layout.fragment_all_user_list) {

    private val userViewModel: UserViewModel by activityViewModels()

    private val userAdapter: UserPagingAdapter by lazy {
        UserPagingAdapter(Constant.UserListType.ALL) { user ->
            userViewModel.updateUserStatus(user)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.rvUserList.adapter = userAdapter
            collectUsers()
            observeLoadState()
            observeErrorState()
            clickListeners()
        }
    }

    private fun clickListeners() {
        binding?.run {
            fbMain.setOnClickListener {
                fbAccepted.setVisible(fbAccepted.toggleVisibility())
                fbRejected.setVisible(fbRejected.toggleVisibility())
            }

            fbAccepted.setOnClickListener {
                findNavController().apply {
                    navigate(AllUserListFragmentDirections.actionAllUserListFragmentToAcceptedUserListFragment())
                }
            }

            fbRejected.setOnClickListener {
                findNavController().apply {
                    navigate(AllUserListFragmentDirections.actionAllUserListFragmentToRejectedUserListFragment())
                }
            }
        }
    }

    private fun observeErrorState() {
        lifecycleScope.launch {
            uiMessageEvents.collectLatest { event ->
                when (event) {
                    is UIComponent.SnackBar -> showSnackBar(message = event.uiText.asString(requireContext()))
                    is UIComponent.Toast -> requireContext().showToast(message = event.uiText.asString(requireContext()))
                    else -> {}
                }
            }
        }
    }

    private fun collectUsers() {
        lifecycleScope.launch {
            userViewModel.allUsersList.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState() {
        userAdapter.addLoadStateListener { loadState ->
            val refresh = loadState.refresh
            when (refresh) {
                is LoadState.Loading -> binding?.pbUserList?.setVisible()
                is LoadState.NotLoading -> binding?.pbUserList?.setGone()
                is LoadState.Error ->{
                    showSnackBar("Error: ${refresh.error.message}")
                    binding?.pbUserList?.setGone()
                }

            }

            val append = loadState.append
            if (append is LoadState.Error) {
                binding?.pbUserList?.setGone()
                showSnackBar("Append Error: ${append.error.message}")
            }
        }
    }


}