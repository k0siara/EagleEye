package com.patrykkosieradzki.eagleeye.network.repositories

import com.patrykkosieradzki.eagleeye.domain.model.TokenCredentialsData
import com.patrykkosieradzki.eagleeye.domain.model.UserCredentialsData
import com.patrykkosieradzki.eagleeye.domain.model.UserInfo
import com.patrykkosieradzki.eagleeye.domain.repositories.LoginRepository
import com.patrykkosieradzki.eagleeye.network.services.AuthorizeCredentials
import com.patrykkosieradzki.eagleeye.network.services.AuthorizeResponse
import com.patrykkosieradzki.eagleeye.network.services.EagleEyeLoginService
import com.patrykkosieradzki.eagleeye.network.services.LoginCredentials
import com.patrykkosieradzki.eagleeye.network.utils.NetworkHandler

class LoginApiRepository(
    private val eagleEyeLoginService: EagleEyeLoginService,
    private val networkHandler: NetworkHandler
) : LoginRepository {
    override suspend fun authenticate(credentials: UserCredentialsData): String {
        return networkHandler.safeNetworkCall {
            eagleEyeLoginService.authenticate(credentials.toNetwork())
        }.token
    }

    override suspend fun authorize(credentials: TokenCredentialsData): UserInfo {
        return networkHandler.safeNetworkCall {
            eagleEyeLoginService.authorize(credentials.toNetwork())
        }.toDomain()
    }

    override suspend fun logout() {

    }
}

fun UserCredentialsData.toNetwork() = LoginCredentials(
    username = username,
    password = password
)

fun TokenCredentialsData.toNetwork() = AuthorizeCredentials(
    token = token
)

fun AuthorizeResponse.toDomain() = UserInfo(
    firstName = firstName,
    lastName = lastName
)