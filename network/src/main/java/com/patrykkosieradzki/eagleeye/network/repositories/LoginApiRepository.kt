package com.patrykkosieradzki.eagleeye.network.repositories

import com.patrykkosieradzki.eagleeye.domain.model.CredentialsData
import com.patrykkosieradzki.eagleeye.domain.repositories.LoginRepository
import com.patrykkosieradzki.eagleeye.network.services.EagleEyeService
import com.patrykkosieradzki.eagleeye.network.services.LoginCredentials
import com.patrykkosieradzki.eagleeye.network.utils.NetworkHandler

class LoginApiRepository(
    private val eagleEyeService: EagleEyeService,
    private val networkHandler: NetworkHandler
) : LoginRepository {
    override suspend fun login(credentials: CredentialsData): String {
        return networkHandler.safeNetworkCall {
            eagleEyeService.login(credentials.toNetwork())
        }.token
    }

    override suspend fun logout() {

    }

}

fun CredentialsData.toNetwork() = LoginCredentials(
    username = username,
    password = password
)