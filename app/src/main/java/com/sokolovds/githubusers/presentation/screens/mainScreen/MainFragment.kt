package com.sokolovds.githubusers.presentation.screens.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokolovds.domain.ApiError
import com.sokolovds.domain.models.onError
import com.sokolovds.domain.models.onLoading
import com.sokolovds.domain.models.onNotLoading
import com.sokolovds.githubusers.presentation.adapters.UserAdapter
import com.sokolovds.githubusers.databinding.MainFragmentBinding
import com.sokolovds.githubusers.presentation.adapters.UserLoadStateAdapter
import com.sokolovds.githubusers.presentation.base.BaseFragment
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainFragment :
    BaseFragment<MainFragmentBinding, MainFragmentViewModel>(),
    KoinComponent {

    override val viewModel by viewModel<MainFragmentViewModel>()
    override val uiErrorHandler by inject<UiErrorHandler>()
    private val adapter: UserAdapter by lazy {
        UserAdapter(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButtons()
        setupEditTextFields()
    }

    private fun setupButtons() {
        binding.tryAgainBtn.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupEditTextFields() {
        binding.searchField.addTextChangedListener {
            viewModel.searchUser(it.toString())
        }
    }

    private fun setupRecyclerView() {
        val adapterWithLoadState =
            adapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter(adapter, uiErrorHandler),
                footer = UserLoadStateAdapter(adapter, uiErrorHandler)
            )

        binding.recyclerView.adapter = adapterWithLoadState
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observeAdapterData()
        observeAdapterDataState()
    }

    private fun observeAdapterData() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeAdapterDataState() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { state ->
                state.refresh
                    .onLoading { setupLoadingState() }
                    .onNotLoading { setupNotLoadingState() }
                    .onError { setupErrorState(it) }
            }
        }
    }

    private fun setupLoadingState() {
        binding.apply {
            progressBar.isVisible = true
            tryAgainBtn.isVisible = false
            errorMsg.isVisible = false
            recyclerView.isVisible = true
        }
    }

    private fun setupErrorState(error: Throwable) {
        binding.errorMsg.isVisible = true
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.errorMsg.text = uiErrorHandler.getString(error)
        if (
            error is ApiError.Network ||
            error is ApiError.Forbidden ||
            error is ApiError.ServiceUnavailable ||
            error is ApiError.Unknown
        ) binding.tryAgainBtn.isVisible = true
    }

    private fun setupNotLoadingState() {
        binding.apply {
            progressBar.isVisible = false
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = MainFragmentBinding.inflate(inflater, container, false)

}