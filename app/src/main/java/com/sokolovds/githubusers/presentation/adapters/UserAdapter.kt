package com.sokolovds.githubusers.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sokolovds.githubusers.databinding.UserItemBinding
import com.sokolovds.githubusers.presentation.loadImage
import com.sokolovds.githubusers.presentation.screens.mainScreen.entities.MainFragmentUserItemEntity

class UserAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<MainFragmentUserItemEntity, UserAdapter.UserViewHolder>(UserComparator()){

    fun interface OnItemClickListener {
        fun onItemClick(login: String)
    }

    inner class UserViewHolder(
        private val binding: UserItemBinding,
        private val listener: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: MainFragmentUserItemEntity) {
            binding.root.setOnClickListener(this)
            with(binding) {
                root.tag = item
                login.text = item.login
                avatar.loadImage(item.avatarUrl, circle = true)
            }
        }

        override fun onClick(view: View?) {
            view?.let {
                val item = it.tag as MainFragmentUserItemEntity
                listener(item.login)
            }
        }
    }

    class UserComparator : DiffUtil.ItemCallback<MainFragmentUserItemEntity>() {
        override fun areItemsTheSame(
            oldItem: MainFragmentUserItemEntity,
            newItem: MainFragmentUserItemEntity
        ) = oldItem.login == newItem.login

        override fun areContentsTheSame(
            oldItem: MainFragmentUserItemEntity,
            newItem: MainFragmentUserItemEntity
        ) = oldItem == newItem
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            UserItemBinding.inflate(inflater, parent, false),
            listener::onItemClick
        )
    }

    fun onRetryPressed() {
        this.retry()
    }


}

