package com.shaadi.presentation.ui.user.rejectedUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.shaadi.R
import com.shaadi.core.CoreFragment
import com.shaadi.data.remote.network.UIComponent
import com.shaadi.data.remote.network.uiMessageEvents
import com.shaadi.data.remote.network.uiProgressEvents
import com.shaadi.databinding.FragmentRejectedUserListBinding
import com.shaadi.presentation.adapter.UserPagingAdapter
import com.shaadi.presentation.viewmodel.UserViewModel
import com.shaadi.utils.Constant
import com.shaadi.utils.setGone
import com.shaadi.utils.setVisible
import com.shaadi.utils.showSnackBar
import com.shaadi.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class RejectedUserListFragment : CoreFragment<FragmentRejectedUserListBinding>(layoutResId = R.layout.fragment_rejected_user_list) {

    private val userViewModel: UserViewModel by activityViewModels()

    private val userAdapter: UserPagingAdapter by lazy {
        UserPagingAdapter(Constant.UserListType.REJECTED) { }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.rvRejectedUserList.adapter = userAdapter
            userViewModel.getAcceptRejectedUserList(isAccepted = false)
            collectUsersRejectedState()
            observeLoadState()
            observeErrorState()
            observeProgressState()
        }
    }

    private fun observeProgressState() {
        binding?.run {
            lifecycleScope.launch {
                uiProgressEvents.collectLatest { event ->
                    withContext(Dispatchers.Main){
                        pbRejectedUserList.isVisible = event.first == true
                        if(event.second){
                            binding?.vsNoData?.let {
                                if(it.viewStub==null){
                                    it.viewStub?.inflate()
                                }else{
                                    it.viewStub?.setVisible()
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun observeErrorState() {
        //This function collects UI message events and displays them as SnackBar or Toast messages but we have done any kind of error handling in this fragment
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

    private fun collectUsersRejectedState() {
        lifecycleScope.launch {
            userViewModel.currentUserListType.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState() {
        userAdapter.addLoadStateListener { loadState ->
            val refresh = loadState.refresh
            when (refresh) {
                is LoadState.Loading -> binding?.pbRejectedUserList?.setVisible()
                is LoadState.NotLoading -> binding?.pbRejectedUserList?.setGone()
                is LoadState.Error -> showSnackBar("Error: ${refresh.error.message}")
            }
            val append = loadState.append
            if (append is LoadState.Error) {
                showSnackBar("Append Error: ${append.error.message}")
            }

            val isListEmpty = refresh is LoadState.NotLoading && userAdapter.itemCount == 0

            if (isListEmpty) {
                binding?.vsNoData?.let {
                    if(it.viewStub==null){
                        it.viewStub?.inflate()
                    }else{
                        it.viewStub?.setVisible()
                    }
                }
            } else {
                // Hide if there is data
                val inflated = binding?.root?.findViewById<View>(R.id.vsNoData) // adjust to root view ID
                inflated?.visibility = View.GONE
            }
        }


    }

}