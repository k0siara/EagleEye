package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.repositories.SessionState
import com.patrykkosieradzki.eagleeye.domain.repositories.UserSessionRepository

interface SaveSessionUseCase {
    suspend fun invoke(sessionState: SessionState)
}

class SaveSessionUseCaseImpl(
    private val sessionRepository: UserSessionRepository
) : SaveSessionUseCase {
    override suspend fun invoke(sessionState: SessionState) {
        sessionRepository.saveSessionState(sessionState)
    }
}