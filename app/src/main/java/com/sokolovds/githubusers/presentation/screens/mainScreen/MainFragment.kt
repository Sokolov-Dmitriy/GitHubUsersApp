package com.sokolovds.githubusers.presentation.screens.mainScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sokolovds.domain.ApiError
import com.sokolovds.domain.models.onError
import com.sokolovds.domain.models.onLoading
import com.sokolovds.domain.models.onNotLoading
import com.sokolovds.githubusers.R
import com.sokolovds.githubusers.presentation.adapters.UserAdapter
import com.sokolovds.githubusers.databinding.MainFragmentBinding
import com.sokolovds.githubusers.di.ViewHandlerEnum
import com.sokolovds.githubusers.presentation.adapters.UserLoadStateAdapter
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler
import com.sokolovds.githubusers.presentation.utils.ViewHandler
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding by viewBinding(MainFragmentBinding::bind)
    private val viewModel by viewModel<MainFragmentViewModel>()
    private val uiErrorHandler by inject<UiErrorHandler>()
    private val adapter: UserAdapter by lazy { UserAdapter { viewModel.onItemClick(it) } }
    private val navigationHandler by inject<ViewHandler>(named(ViewHandlerEnum.NAVIGATION)) {
        parametersOf(
            lifecycleScope,
            viewModel.navActionFlow,
            findNavController()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationHandler.subscribe()
        setupRecyclerView()
        setupButtons()
        setupEditTextFields()
    }

    private fun setupButtons() = with(binding) {
        tryAgainBtn.setOnClickListener { adapter.retry() }
    }

    private fun setupEditTextFields() = with(binding) {
        searchField.addTextChangedListener { viewModel.searchUser(it.toString()) }
    }

    private fun setupRecyclerView() {
        val adapterWithLoadState =
            adapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter(uiErrorHandler) { adapter.onRetryPressed() },
                footer = UserLoadStateAdapter(uiErrorHandler) { adapter.onRetryPressed() }
            )
        binding.recyclerView.adapter = adapterWithLoadState
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observeAdapterData()
        observeAdapterDataState()
    }

    private fun observeAdapterData() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        viewModel.flow.collectLatest { pagingData ->
            adapter.submitData(pagingData)
        }
    }

    private fun observeAdapterDataState() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        adapter.loadStateFlow.collectLatest { state ->
            state.refresh
                .onLoading { setupLoadingState() }
                .onNotLoading { setupNotLoadingState() }
                .onError { setupErrorState(it) }
        }
    }

    private fun setupLoadingState() = with(binding) {
        progressBar.isVisible = true
        tryAgainBtn.isVisible = false
        errorMsg.isVisible = false
        recyclerView.isVisible = false
    }

    private fun setupErrorState(error: Throwable) = with(binding) {
        errorMsg.isVisible = true
        progressBar.isVisible = false
        recyclerView.isVisible = false
        errorMsg.text = uiErrorHandler.getString(error)
        if (
            error is ApiError.Network ||
            error is ApiError.Forbidden ||
            error is ApiError.ServiceUnavailable ||
            error is ApiError.Unknown
        ) tryAgainBtn.isVisible = true
    }

    private fun setupNotLoadingState() = with(binding) {
        progressBar.isVisible = false
        recyclerView.isVisible = true
    }

}