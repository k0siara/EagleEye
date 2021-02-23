package com.patrykkosieradzki.eagleeye.network.services

import com.patrykkosieradzki.eagleeye.network.utils.ApiResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

interface EagleEyeService {
    @POST("g/aaa/authenticate")
    suspend fun login(@Body loginCredentials: LoginCredentials): ApiResult<LoginResponse>

//    @GET("g/device/list")
//    suspend fun getListOfCameras(@Header("Cookie"))
}

@JsonClass(generateAdapter = true)
data class LoginCredentials(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)

data class LoginResponse(
    @Json(name = "token") val token: String
)