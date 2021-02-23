package com.patrykkosieradzki.eagleeye.domain.repositories

import com.patrykkosieradzki.eagleeye.domain.model.TokenCredentialsData
import com.patrykkosieradzki.eagleeye.domain.model.UserCredentialsData
import com.patrykkosieradzki.eagleeye.domain.model.UserInfo

interface LoginRepository {
    suspend fun authenticate(credentials: UserCredentialsData): String
    suspend fun authorize(credentials: TokenCredentialsData): UserInfo
    suspend fun logout()
}