package com.patrykkosieradzki.eagleeye.ui.login

import com.hadilq.liveevent.LiveEvent
import com.patrykkosieradzki.eagleeye.domain.exceptions.ApiException
import com.patrykkosieradzki.eagleeye.domain.usecases.LoginUseCase
import com.patrykkosieradzki.eagleeye.ui.utils.*
import com.patrykkosieradzki.eagleeye.utils.*

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginViewState>(
    initialState = LoginViewState(inProgress = false)
) {
    val loggedInEvent = LiveEvent<Unit>()

    fun onUsernameTextChanged(username: String) {
        updateViewState { it.copy(username = username) }
    }

    fun onPasswordTextChanged(password: String) {
        updateViewState { it.copy(password = password) }
    }

    fun onLoginButtonClicked() {
        safeLaunch {
            updateViewState { it.copy(inProgress = true) }
            loginUseCase.invoke(
                viewState.valueNN.username,
                viewState.valueNN.password
            )
            loggedInEvent.fireEvent()
            updateViewState { it.toSuccess() }
        }
    }

    override fun updateError(exception: Throwable): ErrorEvent {
        if (exception is ApiException.UnknownApiException) {
            if (exception.errorMessage == LOGIN_DATA_INCORRECT) {
                return ErrorEvent(ApiException.OtherError(LOGIN_DATA_INCORRECT_MESSAGE))
            }
        }
        return super.updateError(exception)
    }

    companion object {
        const val LOGIN_DATA_INCORRECT = "Unauthorized"
        const val LOGIN_DATA_INCORRECT_MESSAGE = "Invalid credentials, try again."
    }
}

data class LoginViewState(
    val username: String = "",
    val password: String = "",
    override val inProgress: Boolean
) : ViewState {
    override fun toSuccess() = copy(inProgress = false)
}