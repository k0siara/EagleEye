package com.patrykkosieradzki.eagleeye.network.repositories

import com.patrykkosieradzki.eagleeye.domain.model.Camera
import com.patrykkosieradzki.eagleeye.domain.model.CameraDetails
import com.patrykkosieradzki.eagleeye.domain.model.CameraSettings
import com.patrykkosieradzki.eagleeye.domain.repositories.CameraRepository
import com.patrykkosieradzki.eagleeye.network.services.CameraDetailsResponse
import com.patrykkosieradzki.eagleeye.network.services.EagleEyeCameraService
import com.patrykkosieradzki.eagleeye.network.utils.NetworkHandler

class CameraApiRepository(
    private val cameraService: EagleEyeCameraService,
    private val networkHandler: NetworkHandler
) : CameraRepository {
    override suspend fun getListOfCameras(): List<Camera> {
        return networkHandler.safeNetworkCall {
            cameraService.getListOfCameras()
        }.toDomain()
    }

    override suspend fun getCameraDetails(cameraId: String): CameraDetails {
        return networkHandler.safeNetworkCall {
            cameraService.getCameraDetails(cameraId)
        }.toDomain()
    }
}

fun List<List<Any>>.toDomain(): List<Camera> {
    val cameras = ArrayList<Camera>()
    for (camera in this) {
        println(camera)
        if (camera[1] != null) {
            cameras.add(Camera(
                id = camera[1].toString(),
                name = camera[2].toString()
            ))
        }
    }
    return cameras
}

fun CameraDetailsResponse.toDomain() = CameraDetails(
    id = id,
    name = name,
    guid = guid ?: NOT_PROVIDED,
    tags = tags,
    settings = CameraSettings(
        username = settings?.username ?: NOT_PROVIDED,
        password = settings?.password ?: NOT_PROVIDED,
        notes = settings?.notes ?: NOT_PROVIDED
    )
)

internal const val NOT_PROVIDED = "NOT PROVIDED"