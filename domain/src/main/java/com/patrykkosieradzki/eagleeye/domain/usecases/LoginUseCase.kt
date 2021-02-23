package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.model.CredentialsData
import com.patrykkosieradzki.eagleeye.domain.repositories.*

interface LoginUseCase {
    suspend fun invoke(username: String, password: String)
}

class LoginUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val userSessionRepository: UserSessionRepository,
    private val timestampProvider: TimestampProvider
) : LoginUseCase {
    override suspend fun invoke(username: String, password: String) {
        val token = loginRepository.login(CredentialsData(
            username = username,
            password = password
        ))
        userSessionRepository.saveSessionState(SessionState(
            token = token,
            validUntilTimestamp = timestampProvider.getTimestampInFuture(TOKEN_EXPIRATION)
        ))
    }

    companion object {
        const val TOKEN_EXPIRATION = 30
    }
}