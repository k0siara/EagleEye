package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.model.CameraDetails
import com.patrykkosieradzki.eagleeye.domain.repositories.CameraRepository

interface GetCameraDetailsUseCase {
    suspend fun invoke(cameraId: String): CameraDetails
}

class GetCameraDetailsUseCaseImpl(
    private val cameraRepository: CameraRepository
) : GetCameraDetailsUseCase {
    override suspend fun invoke(cameraId: String): CameraDetails {
        return cameraRepository.getCameraDetails(cameraId)
    }
}