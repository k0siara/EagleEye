package com.patrykkosieradzki.eagleeye.network.services

import com.patrykkosieradzki.eagleeye.network.utils.ApiResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

interface EagleEyeLoginService {
    @POST("g/aaa/authenticate")
    suspend fun authenticate(@Body credentials: LoginCredentials): ApiResult<AuthenticateResponse>

    @POST("g/aaa/authorize")
    suspend fun authorize(@Body credentials: AuthorizeCredentials): ApiResult<AuthorizeResponse>
}

@JsonClass(generateAdapter = true)
data class LoginCredentials(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)

@JsonClass(generateAdapter = true)
data class AuthenticateResponse(
    @Json(name = "token") val token: String
)

@JsonClass(generateAdapter = true)
data class AuthorizeCredentials(
    @Json(name = "token") val token: String
)

@JsonClass(generateAdapter = true)
data class AuthorizeResponse(
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
)