package com.patrykkosieradzki.eagleeye.ui.cameras.details

import com.patrykkosieradzki.eagleeye.domain.model.CameraDetails
import com.patrykkosieradzki.eagleeye.domain.model.CameraSettings
import com.patrykkosieradzki.eagleeye.utils.*
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.declareMock

class CameraDetailsFragmentShould : RobotTest<CameraDetailsFragmentRobot>() {

    @get:Rule
    val mockRule = mockViewModelRule<CameraDetailsViewModel, CameraDetailsViewState>(
        defaultViewState = CameraDetailsViewState(inProgress = false)
    )

    @get:Rule
    val rule = fragmentTestRuleWithMocks{
        declareMock<CameraDetailsViewModel>()
    }

    @Test
    fun showCameraDetailsScreen() {
        withRobot {
            startFragment(CameraDetailsFragment().apply {
                arguments = CameraDetailsFragmentArgs("1").toBundle()
            })
            capture("03_Camera_Details_Empty")
            wait(1)
            setViewState(
                CameraDetailsViewState(
                    cameraDetails = CameraDetails(
                        id = "1",
                        name = "First camera",
                        guid = "Some guuid",
                        tags = emptyList(),
                        settings = CameraSettings(
                            username = "User",
                            password = "pass",
                            notes = "notes..."
                        )
                    ),
                    inProgress = false
                )
            )
            capture("03_Camera_Details_Filled")
            wait(1)
        }
    }

    override fun createRobot() = CameraDetailsFragmentRobot(rule)
}

class CameraDetailsFragmentRobot(
    rule: FragmentTestRule
) : FragmentRobot<CameraDetailsViewState, CameraDetailsViewModel>(rule)