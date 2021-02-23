package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.model.TokenCredentialsData
import com.patrykkosieradzki.eagleeye.domain.model.UserCredentialsData
import com.patrykkosieradzki.eagleeye.domain.repositories.*

interface LoginUseCase {
    suspend fun invoke(username: String, password: String)
}

class LoginUseCaseImpl(
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override suspend fun invoke(username: String, password: String) {
        val token = loginRepository.authenticate(UserCredentialsData(
            username = username,
            password = password
        ))
        loginRepository.authorize(TokenCredentialsData(token))
    }
}