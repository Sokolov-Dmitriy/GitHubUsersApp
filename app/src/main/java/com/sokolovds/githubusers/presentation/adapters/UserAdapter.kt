package com.sokolovds.githubusers.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sokolovds.domain.models.UserItem
import com.sokolovds.githubusers.R
import com.sokolovds.githubusers.databinding.UserItemBinding

class UserAdapter(private val listener: ClickListener) :
    PagingDataAdapter<UserItem, UserAdapter.UserViewHolder>(UserComparator()),
    UserLoadStateAdapter.RetryListener {
    interface ClickListener {
        fun onItemClick(login: String)
    }

    inner class UserViewHolder(
        private val binding: UserItemBinding,
        private val listener: ClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: UserItem) {
            binding.root.setOnClickListener(this)
            with(binding) {
                root.tag = item
                login.text = item.login
                val context = avatar.context
                Glide
                    .with(context)
                    .load(item.avatarUrl)
                    .placeholder(R.drawable.ic_default_avatar)
                    .error(R.drawable.ic_default_avatar)
                    .into(avatar)
            }
        }

        override fun onClick(p0: View?) {
            p0?.let {
                val item = it.tag as UserItem
                listener.onItemClick(item.login)
            }
        }
    }

    class UserComparator : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            UserItemBinding.inflate(inflater, parent, false),
            listener
        )
    }

    override fun onRetryPressed() {
        this.retry()
    }


}
