package com.patrykkosieradzki.eagleeye.network.services

import com.patrykkosieradzki.eagleeye.network.utils.ApiResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query

interface EagleEyeCameraService {
    @GET("g/device/list")
    suspend fun getListOfCameras(): ApiResult<List<List<Any>>>

    @GET("g/device")
    suspend fun getCameraDetails(@Query("id") cameraId: String): ApiResult<CameraDetailsResponse>
}

@JsonClass(generateAdapter = true)
data class CameraDetailsResponse(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "guid") val guid: String?,
    @Json(name = "tags") val tags: List<String>,
    @Json(name = "settings") val settings: CameraSettingsResponse?
)

@JsonClass(generateAdapter = true)
data class CameraSettingsResponse(
    @Json(name = "username") val username: String?,
    @Json(name = "password") val password: String?,
    @Json(name = "notes") val notes: String?,
)
