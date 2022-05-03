package com.sokolovds.githubusers.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sokolovds.githubusers.presentation.navigation.NavigationAction
import com.sokolovds.githubusers.presentation.navigation.observeNonNull
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler

abstract class BaseFragment<B : ViewBinding, VM : BaseViewModel> : Fragment() {
    abstract val viewModel: VM
    abstract val uiErrorHandler: UiErrorHandler
    private var _binding: B? = null
    protected val binding get() = checkNotNull(_binding)

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navAction: NavigationAction) {
        when (navAction) {
            is NavigationAction.ToDirection -> findNavController().navigate(navAction.directions)
            is NavigationAction.Back -> findNavController().navigateUp()
        }
    }

}