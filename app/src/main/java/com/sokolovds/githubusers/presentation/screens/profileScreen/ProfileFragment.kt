package com.sokolovds.githubusers.presentation.screens.profileScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sokolovds.domain.utils.ApiError
import com.sokolovds.githubusers.R
import com.sokolovds.githubusers.databinding.ProfileFragmentBinding
import com.sokolovds.githubusers.di.ViewHandlerEnum
import com.sokolovds.githubusers.presentation.loadImage
import com.sokolovds.githubusers.presentation.screens.profileScreen.entities.ProfileFragmentUserEntity
import com.sokolovds.githubusers.presentation.setInView
import com.sokolovds.githubusers.presentation.utils.UiErrorHandler
import com.sokolovds.githubusers.presentation.utils.ViewHandler
import com.sokolovds.githubusers.presentation.utils.stateHandler.StateHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private val binding by viewBinding(ProfileFragmentBinding::bind)
    private val viewModel by viewModel<ProfileFragmentViewModel>()
    private val uiErrorHandler by inject<UiErrorHandler>()
    private val args by navArgs<ProfileFragmentArgs>()
    private val userStateHandler by inject<ViewHandler>(named(ViewHandlerEnum.USER_PROFILE)) {
        parametersOf(
            lifecycleScope,
            viewModel.userState,
            userStateHandlerImpl
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userStateHandler.subscribe()
        setupButtons()
    }

    private fun setupButtons() = with(binding) {
        tryAgainBtn.setOnClickListener { viewModel.onTryAgainPressed() }
    }

    private fun setupContent(data: ProfileFragmentUserEntity, visible: Boolean = true) =
        with(binding) {
            data.location.setInView(locationGroup, location, visible = visible)
            data.website.setInView(websiteGroup, website, visible = visible)
            data.email.setInView(emailGroup, email, visible = visible)
            data.company.setInView(companyGroup, company, visible = visible)
            data.name.setInView(headerGroup, name, visible = visible)
            data.login.setInView(headerGroup, login, visible = visible)
            data.createdAt.setInView(dateGroup, date, visible = visible)
            getString(R.string.follow, data.followersCount, data.followingCount).setInView(
                followGroup,
                follow,
                visible = visible
            )
            getString(R.string.created_at, data.createdAt).setInView(
                dateGroup,
                date,
                visible = visible
            )
            avatar.loadImage(data.avatarUrl)
        }


    private val userStateHandlerImpl =
        object : StateHandler.HandlerImplementation<ProfileFragmentUserEntity> {
            override fun onSuccessState(data: ProfileFragmentUserEntity) {
                showError(false)
                showProgressBar(false)
                showProfileInformation(true, data)
            }

            override fun onErrorState(error: ApiError) {
                showError(true, error)
                showProgressBar(false)
                showProfileInformation(false)
            }

            override fun onLoadingState() {
                showError(false)
                showProgressBar(true)
                showProfileInformation(false)
            }

            override fun setupStartConfiguration() {
                viewModel.loadUserData(args.userLogin)
            }
        }

    private fun showProfileInformation(
        isVisible: Boolean = false,
        data: ProfileFragmentUserEntity? = null
    ) {
        if (!isVisible) {
            with(binding) {
                locationGroup.isVisible = isVisible
                headerGroup.isVisible = isVisible
                dateGroup.isVisible = isVisible
                followGroup.isVisible = isVisible
                companyGroup.isVisible = isVisible
                emailGroup.isVisible = isVisible
                websiteGroup.isVisible = isVisible
            }
        } else if (data != null) setupContent(data)
    }

    private fun showError(isVisible: Boolean = false, error: ApiError? = null) = with(binding) {
        errorGroup.isVisible = isVisible
        if (isVisible && error != null) errorMsg.text = uiErrorHandler.getString(error)
    }

    private fun showProgressBar(isVisible: Boolean = false) = with(binding) {
        progressBar.isVisible = isVisible
    }

}


