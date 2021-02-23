package com.patrykkosieradzki.eagleeye.domain.repositories

import com.patrykkosieradzki.eagleeye.domain.model.Camera
import com.patrykkosieradzki.eagleeye.domain.model.CameraDetails

interface CameraRepository {
    suspend fun getListOfCameras(): List<Camera>
    suspend fun getCameraDetails(cameraId: String): CameraDetails
}