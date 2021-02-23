package com.patrykkosieradzki.eagleeye.di

import com.patrykkosieradzki.eagleeye.EagleEyeAppConfiguration
import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.patrykkosieradzki.eagleeye.domain.usecases.*
import com.patrykkosieradzki.eagleeye.ui.cameras.details.CameraDetailsViewModel
import com.patrykkosieradzki.eagleeye.ui.cameras.main.CameraListViewModel
import com.patrykkosieradzki.eagleeye.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AppConfiguration> {
        EagleEyeAppConfiguration()
    }

    factory<CheckSessionUseCase> {
        CheckSessionUseCaseImpl()
    }

    factory<ClearSessionUseCase> {
        ClearSessionUseCaseImpl()
    }

    factory<LoginUseCase> {
        LoginUseCaseImpl(
            loginRepository = get()
        )
    }

    viewModel { LoginViewModel(
        loginUseCase = get()
    ) }

    viewModel { CameraListViewModel() }

    viewModel { CameraDetailsViewModel() }
}