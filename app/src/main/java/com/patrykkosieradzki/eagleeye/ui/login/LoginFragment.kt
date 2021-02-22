package com.patrykkosieradzki.eagleeye.ui.login

import android.view.View
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.databinding.LoginFragmentBinding
import com.patrykkosieradzki.eagleeye.ui.utils.BaseFragment

class LoginFragment : BaseFragment<LoginViewState, LoginViewModel, LoginFragmentBinding>(
    R.layout.login_fragment, LoginViewModel::class
) {
    override fun setupViews(view: View) {
        super.setupViews(view)

    }
}