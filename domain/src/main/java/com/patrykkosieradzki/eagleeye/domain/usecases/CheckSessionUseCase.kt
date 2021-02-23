package com.patrykkosieradzki.eagleeye.domain.usecases

import java.io.IOException

interface CheckSessionUseCase {
    fun invoke()
}

class CheckSessionUseCaseImpl : CheckSessionUseCase {
    override fun invoke() {

    }
}

class SessionTimeoutException : IOException()