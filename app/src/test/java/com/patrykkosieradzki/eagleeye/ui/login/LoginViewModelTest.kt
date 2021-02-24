package com.patrykkosieradzki.eagleeye.ui.login

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.patrykkosieradzki.eagleeye.domain.exceptions.ApiException
import com.patrykkosieradzki.eagleeye.domain.usecases.LoginUseCase
import com.patrykkosieradzki.eagleeye.ui.login.LoginViewModel.Companion.LOGIN_DATA_INCORRECT_MESSAGE
import com.patrykkosieradzki.eagleeye.ui.utils.ErrorEvent
import com.patrykkosieradzki.eagleeye.ui.utils.valueNN
import com.patrykkosieradzki.eagleeye.utils.BaseJunit4Test
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class LoginViewModelTest : BaseJunit4Test() {

    @Mock
    lateinit var loginUseCase: LoginUseCase

    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `should update username`() {
        val username = "Username"

        viewModel.onUsernameTextChanged(username)

        assertThat(viewModel.viewState.valueNN.username).isEqualTo(username)
    }

    @Test
    fun `should update password`() {
        val password = "Pass"

        viewModel.onPasswordTextChanged(password)

        assertThat(viewModel.viewState.valueNN.password).isEqualTo(password)
    }

    @Test
    fun `on successful login should go to desktop`() = runBlockingTest {
        viewModel.onUsernameTextChanged("user")
        viewModel.onPasswordTextChanged("pass")

        viewModel.onLoginButtonClicked()

        verify(loginUseCase, times(1)).invoke("user", "pass")
        assertThat(viewModel.loggedInEvent.valueNN).isEqualTo(Unit)
    }

    @Test
    fun `on ApiException UnknownApiException should show dialog`() = runBlockingTest {
        whenever(loginUseCase.invoke(any(), any())).thenThrow(ApiException.UnknownApiException(
            LoginViewModel.LOGIN_DATA_INCORRECT
        ))

        viewModel.onLoginButtonClicked()

        assertThat(viewModel.showErrorEvent.valueNN.throwable).isInstanceOf(ApiException.OtherError::class.java)
        assertThat(viewModel.showErrorEvent.valueNN.throwable?.message).isEqualTo(LOGIN_DATA_INCORRECT_MESSAGE)
    }
}