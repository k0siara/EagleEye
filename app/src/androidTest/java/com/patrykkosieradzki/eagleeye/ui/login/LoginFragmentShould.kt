package com.patrykkosieradzki.eagleeye.ui.login

import com.patrykkosieradzki.eagleeye.utils.*
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.declareMock

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
            setViewState(LoginViewState(
                    username = "eagle@eye.com",
                    password = "super_password",
                    inProgress = false
            ))
            capture("01_Login_Screen_Filled")
            wait(15)
        }
    }

    override fun createRobot() = LoginFragmentRobot(rule)
}

class LoginFragmentRobot(
        rule: FragmentTestRule
) : FragmentRobot<LoginViewState, LoginViewModel>(rule) {

}