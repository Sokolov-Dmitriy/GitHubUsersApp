package com.sokolovds.githubusers.presentation.screens.profileScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sokolovds.domain.ApiError
import com.sokolovds.domain.models.User
import com.sokolovds.domain.models.onError
import com.sokolovds.domain.models.onLoading
import com.sokolovds.domain.models.onSuccess
import com.sokolovds.githubusers.R
import com.sokolovds.githubusers.databinding.ProfileFragmentBinding
import com.sokolovds.githubusers.presentation.base.BaseFragment
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileFragment :
    BaseFragment<ProfileFragmentBinding, ProfileFragmentViewModel>(),
    KoinComponent {

    override val viewModel by viewModel<ProfileFragmentViewModel>()
    override val uiErrorHandler by inject<UiErrorHandler>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ProfileFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupButtons()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.userData.collectLatest { result ->
                result
                    .onLoading { onLoadingState() }
                    .onSuccess { onSuccessState(it) }
                    .onError { onErrorState(it) }
            }
        }
    }

    private fun setupButtons() {
        binding.tryAgainBtn.setOnClickListener {
            viewModel.onTryAgainPressed()
        }
    }

    private fun onLoadingState() {
        binding.progressBar.isVisible = true
        binding.errorGroup.isVisible = false
    }

    private fun onErrorState(error: ApiError) {
        binding.progressBar.isVisible = false
        binding.errorGroup.isVisible = true
        binding.errorMsg.text = uiErrorHandler.getString(error)
    }

    private fun onSuccessState(data: User) {
        binding.progressBar.isVisible = false
        binding.errorGroup.isVisible = false
        setupContent(data)
    }

    private fun setupContent(data: User, visible: Boolean = true) {
        with(binding) {
            data.location.setInView(locationGroup, location, visible = visible)
            data.website.setInView(websiteGroup, website, visible = visible)
            data.email.setInView(emailGroup, email, visible = visible)
            data.company.setInView(companyGroup, company, visible = visible)
            data.name.setInView(headerGroup, name, visible = visible)
            data.login.setInView(headerGroup, login, visible = visible)
            data.createdAt.setInView(
                dateGroup,
                date,
                getString(R.string.created_at, data.createdAt),
                visible = visible
            )
            data.login.setInView(
                followGroup,
                follow,
                getString(R.string.follow, data.followersCount, data.followingCount),
                visible = visible
            )
            setupImageView(data.avatarUrl, avatar, R.drawable.ic_default_avatar)
        }
    }

    private fun setupImageView(url: String, imageView: ImageView, defaultImage: Int) {
        Glide.with(requireContext())
            .load(url)
            .placeholder(defaultImage)
            .error(defaultImage)
            .into(imageView)
    }


}

fun String?.setInView(
    group: Group,
    textView: TextView,
    text: String? = this,
    visible: Boolean = true
) {
    this?.let {
        group.isVisible = visible
        textView.text = text
    }
}

