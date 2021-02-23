package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.repositories.TimestampProvider
import com.patrykkosieradzki.eagleeye.domain.repositories.UserSessionRepository

interface GetSessionUseCase {
    fun invoke(): String?
}

class GetSessionUseCaseImpl(
    private val userSessionRepository: UserSessionRepository,
    private val timestampProvider: TimestampProvider
) : GetSessionUseCase {
    override fun invoke(): String? {
        val sessionState = userSessionRepository.getSessionState()
        if (sessionState.token != null) {
            if (timestampProvider.isExpired(sessionState.validUntilTimestamp)) {
                throw SessionTimeoutException()
            }
            return sessionState.token
        }
        return null
    }
}