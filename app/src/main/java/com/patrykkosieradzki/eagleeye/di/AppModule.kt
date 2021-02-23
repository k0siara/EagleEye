package com.patrykkosieradzki.eagleeye.di

import com.patrykkosieradzki.eagleeye.EagleEyeAppConfiguration
import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.patrykkosieradzki.eagleeye.domain.repositories.InMemoryUserSessionRepository
import com.patrykkosieradzki.eagleeye.domain.repositories.TimestampProvider
import com.patrykkosieradzki.eagleeye.domain.repositories.TimestampProviderImpl
import com.patrykkosieradzki.eagleeye.domain.repositories.UserSessionRepository
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

    single<TimestampProvider> {
        TimestampProviderImpl()
    }

    single<UserSessionRepository> {
        InMemoryUserSessionRepository()
    }

    factory<GetSessionUseCase> {
        GetSessionUseCaseImpl(
            userSessionRepository = get(),
            timestampProvider = get()
        )
    }

    factory<CheckSessionUseCase> {
        CheckSessionUseCaseImpl(
            clearSessionUseCase = get(),
            getSessionUseCase = get()
        )
    }

    factory<ClearSessionUseCase> {
        ClearSessionUseCaseImpl(
            sessionRepository = get()
        )
    }

    factory<LoginUseCase> {
        LoginUseCaseImpl(
            loginRepository = get(),
            userSessionRepository = get(),
            timestampProvider = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

    viewModel { CameraListViewModel() }

    viewModel { CameraDetailsViewModel() }
}