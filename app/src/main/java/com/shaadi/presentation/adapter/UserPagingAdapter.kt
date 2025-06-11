package com.shaadi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shaadi.databinding.AcceptedUserRowBinding
import com.shaadi.databinding.AllUserItemRowBinding
import com.shaadi.databinding.RejectedUserRowBinding
import com.shaadi.domain.model.UserData
import com.shaadi.utils.Constant.IS_IMAGE_LEGAL_POLICY
import com.shaadi.utils.Constant.UserListType
import com.shaadi.utils.loadProfileImage
import com.shaadi.utils.setGone
import com.shaadi.utils.setVisible

class UserPagingAdapter(
    private val listType: UserListType,
    private val onStatusClick: (UserData) -> Unit
) : PagingDataAdapter<UserData, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> AllUserViewHolder(AllUserItemRowBinding.inflate(inflater, parent, false))
            1 -> AcceptedUserViewHolder(AcceptedUserRowBinding.inflate(inflater, parent, false))
            2 -> RejectedUserViewHolder(RejectedUserRowBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position) ?: return
        when (holder) {
            is AllUserViewHolder -> holder.bind(user)
            is AcceptedUserViewHolder -> holder.bind(user)
            is RejectedUserViewHolder -> holder.bind(user)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            UserListType.ALL -> 0
            UserListType.ACCEPTED -> 1
            UserListType.REJECTED -> 2
            UserListType.UNKNOWN -> 0
        }
    }


    inner class AllUserViewHolder(private val binding: AllUserItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userData: UserData) = with(binding) {
            binding.user = userData
            if (IS_IMAGE_LEGAL_POLICY) {
                ivProfile.setGone()
                tvAvatar.apply {
                    setVisible()
                    text =  buildString {
                        user?.name?.first?.firstOrNull()?.let { append(it.uppercaseChar()) }
                        user?.name?.last?.firstOrNull()?.let { append(it.uppercaseChar()) }
                    }
                }
            } else {
                tvAvatar.setGone()
                ivProfile.apply {
                    setVisible()
                    loadProfileImage(userData.picture?.large, userData.picture?.medium, userData.picture?.thumbnail)
                }
            }

            btnAccept.setOnClickListener {
                onStatusClick(userData.copy(status = UserListType.ACCEPTED.toString()))
            }
            btnReject.setOnClickListener {
                onStatusClick(userData.copy(status = UserListType.REJECTED.toString()))
            }
        }
    }


    inner class AcceptedUserViewHolder(private val binding: AcceptedUserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) = with(binding) {
            this.user = userData
            if (IS_IMAGE_LEGAL_POLICY) {
                ivProfile.setGone()
                tvAvatar.apply {
                    setVisible()
                    text =  buildString {
                        user?.name?.first?.firstOrNull()?.let { append(it.uppercaseChar()) }
                        user?.name?.last?.firstOrNull()?.let { append(it.uppercaseChar()) }
                    }
                }
            } else {
                tvAvatar.setGone()
                ivProfile.apply {
                    setVisible()
                    loadProfileImage(userData.picture?.large, userData.picture?.medium, userData.picture?.thumbnail)
                }
            }
        }

    }

    inner class RejectedUserViewHolder(private val binding: RejectedUserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) = with(binding) {
            this.user = userData
            if (IS_IMAGE_LEGAL_POLICY) {
                ivProfile.setGone()
                tvAvatar.apply {
                    setVisible()
                    text =  buildString {
                        user?.name?.first?.firstOrNull()?.let { append(it.uppercaseChar()) }
                        user?.name?.last?.firstOrNull()?.let { append(it.uppercaseChar()) }
                    }
                }
            } else {
                tvAvatar.setGone()
                ivProfile.apply {
                    setVisible()
                    loadProfileImage(userData.picture?.large, userData.picture?.medium, userData.picture?.thumbnail)
                }
            }
        }

    }


    object DiffCallback : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(old: UserData, new: UserData): Boolean = old.login?.uuid == new.login?.uuid
        override fun areContentsTheSame(old: UserData, new: UserData): Boolean = old == new
    }
}
