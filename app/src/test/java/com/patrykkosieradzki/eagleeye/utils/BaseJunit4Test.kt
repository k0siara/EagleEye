package com.patrykkosieradzki.eagleeye.utils

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations


class BaseJunit4Test {
//    protected val testCoroutineDispatcher = TestCoroutineDispatcher()

//    @get:Rule
//    val instantTaskExecutorRule: InstantTaskExecutorRule()

//    @get:Rule
//    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun baseSetup() {
        MockitoAnnotations.initMocks(this)
//        EagleEyeDispatchers.IO = testCoroutineDispatcher
    }

    @After
    fun baseTearDown() {
        EagleEyeDispatchers.resetAll()
    }
}