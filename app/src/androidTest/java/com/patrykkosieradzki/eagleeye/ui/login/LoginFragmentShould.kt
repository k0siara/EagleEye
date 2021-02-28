package com.patrykkosieradzki.eagleeye.ui.login

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.patrykkosieradzki.eagleeye.ui.utils.ErrorEvent
import com.patrykkosieradzki.eagleeye.ui.utils.fireEvent
import com.patrykkosieradzki.eagleeye.utils.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.mock.declareMock

@RunWith(AndroidJUnit4::class)
class LoginFragmentShould : RobotTest<LoginFragmentRobot>() {

    @get:Rule
    val mockRule = mockViewModelRule<LoginViewModel, LoginViewState>(
            defaultViewState = LoginViewState(inProgress = false)
    )

    @get:Rule
    val rule = fragmentTestRuleWithMocks{
        declareMock<LoginViewModel>()
    }

    @Test
    fun showLoginScreen() {
        withRobot {
            startFragment(LoginFragment())
            capture("01_Login_Screen_Empty")
            wait(1)
            setViewState(LoginViewState(
                    username = "eagle@eye.com",
                    password = "super_password",
                    inProgress = false
            ))
            capture("01_Login_Screen_Filled")
            wait(1)
            onViewModel {
                showErrorEvent.fireEvent(ErrorEvent(LoginViewModel.LOGIN_DATA_INCORRECT))
            }
            capture("01_Login_Screen_Error", waitForCaptureInMs = 1000)
            wait(1)
        }
    }

    override fun createRobot() = LoginFragmentRobot(rule)
}

class LoginFragmentRobot(
        rule: FragmentTestRule
) : FragmentRobot<LoginViewState, LoginViewModel>(rule) {

}