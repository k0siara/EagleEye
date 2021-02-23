package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.repositories.UserSessionRepository

interface ClearSessionUseCase {
    fun invoke()
}

class ClearSessionUseCaseImpl(
    private val sessionRepository: UserSessionRepository
) : ClearSessionUseCase {
    override fun invoke() {
        sessionRepository.clearSessionState()
    }
}