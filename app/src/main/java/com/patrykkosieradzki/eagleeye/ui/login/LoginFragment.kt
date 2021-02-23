package com.patrykkosieradzki.eagleeye.ui.login

import android.view.View
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.databinding.LoginFragmentBinding
import com.patrykkosieradzki.eagleeye.ui.utils.BaseFragment
import com.patrykkosieradzki.eagleeye.ui.utils.observeText

class LoginFragment : BaseFragment<LoginViewState, LoginViewModel, LoginFragmentBinding>(
    R.layout.login_fragment, LoginViewModel::class
) {
    override fun setupViews(view: View) {
        super.setupViews(view)
        with(binding) {
            username.observeText { viewModel.onUsernameTextChanged(it) }
            password.observeText { viewModel.onPasswordTextChanged(it) }
        }
        with(viewModel) {
            loggedInEvent.observe(viewLifecycleOwner) {
                goToDesktop()
            }
        }
    }

    override val requireSession = false
}