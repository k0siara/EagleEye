package com.patrykkosieradzki.eagleeye.domain.repositories

import com.patrykkosieradzki.eagleeye.domain.model.CredentialsData

interface LoginRepository {
    suspend fun login(credentials: CredentialsData): String
    suspend fun logout()
}