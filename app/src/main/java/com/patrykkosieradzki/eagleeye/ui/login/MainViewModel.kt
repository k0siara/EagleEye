package com.patrykkosieradzki.eagleeye.ui.login

import com.patrykkosieradzki.eagleeye.ui.utils.BaseViewModel
import com.patrykkosieradzki.eagleeye.ui.utils.ViewState

class LoginViewModel(

) : BaseViewModel<LoginViewState>(
    initialState = LoginViewState(inProgress = true)
) {
    override fun initialize() {

    }
}

data class LoginViewState(
    override val inProgress: Boolean
) : ViewState {
    override fun toSuccess() = copy(inProgress = false)
}