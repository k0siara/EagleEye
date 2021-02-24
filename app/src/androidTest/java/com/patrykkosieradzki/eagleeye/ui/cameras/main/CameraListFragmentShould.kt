package com.patrykkosieradzki.eagleeye.ui.cameras.main

import com.patrykkosieradzki.eagleeye.domain.model.Camera
import com.patrykkosieradzki.eagleeye.utils.*
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.declareMock

class CameraListFragmentShould : RobotTest<CameraListFragmentRobot>() {

    @get:Rule
    val mockRule = mockViewModelRule<CameraListViewModel, CameraListViewState>(
            defaultViewState = CameraListViewState(inProgress = false)
    )

    @get:Rule
    val rule = fragmentTestRuleWithMocks{
        declareMock<CameraListViewModel>()
    }

    @Test
    fun showListOfCamerasScreen() {
        withRobot {
            startFragment(CameraListFragment())
            capture("02_Camera_List_Empty")
            wait(1)
            setViewState(CameraListViewState(
                cameras = listOf(
                    Camera("1", "First camera"),
                    Camera("2", "Second camera"),
                    Camera("3", "Third camera"),
                ),
                inProgress = false
            ))
            capture("02_Camera_List_Filled")
            wait(1)
        }
    }

    override fun createRobot() = CameraListFragmentRobot(rule)
}

class CameraListFragmentRobot(
        rule: FragmentTestRule
) : FragmentRobot<CameraListViewState, CameraListViewModel>(rule)