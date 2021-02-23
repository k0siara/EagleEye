package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.model.CredentialsData
import com.patrykkosieradzki.eagleeye.domain.repositories.LoginRepository

interface LoginUseCase {
    suspend fun invoke(username: String, password: String)
}

class LoginUseCaseImpl(
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override suspend fun invoke(username: String, password: String) {
        val token = loginRepository.login(CredentialsData(
            username = username,
            password = password
        ))
    }
}