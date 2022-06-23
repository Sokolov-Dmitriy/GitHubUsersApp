package com.sokolovds.githubusers.presentation.screens.mainScreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sokolovds.domain.models.onError
import com.sokolovds.domain.models.onLoading
import com.sokolovds.domain.models.onNotLoading
import com.sokolovds.githubusers.databinding.LoadStateAdapterBinding
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler

class UserLoadStateAdapter(
    private val uiErrorHandler: UiErrorHandler,
    private val listener: OnRetryClickListener,
) : LoadStateAdapter<UserLoadStateAdapter.StateViewHolder>() {

    inner class StateViewHolder(
        private val binding: LoadStateAdapterBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            setupButtons()
            loadState
                .onLoading { setupLoadingState() }
                .onNotLoading { setupNotLoadingState() }
                .onError { setupErrorState(it) }
        }

        private fun setupButtons() = binding.errorView.setOnClickListener {
            listener.onRetryPressed()
        }

        private fun setupLoadingState() = with(binding) {
            progressBar.isVisible = true
            errorView.btnIsVisible = false
            errorView.errorMsgIsVisible = false
        }

        private fun setupErrorState(error: Throwable) = with(binding) {
            errorView.btnIsVisible = true
            errorView.errorMsgIsVisible = true
            progressBar.isVisible = false
            errorView.errorText = uiErrorHandler.getString(error)
        }


        private fun setupNotLoadingState() = with(binding) {
            progressBar.isVisible = false
        }

    }

    override fun onBindViewHolder(holder: StateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    fun interface OnRetryClickListener {
        fun onRetryPressed()
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoadStateAdapterBinding.inflate(inflater, parent, false)
        return StateViewHolder(binding)
    }
}