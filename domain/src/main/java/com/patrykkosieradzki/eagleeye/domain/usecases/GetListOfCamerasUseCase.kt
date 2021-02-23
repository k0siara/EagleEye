package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.model.Camera
import com.patrykkosieradzki.eagleeye.domain.repositories.CameraRepository

interface GetListOfCamerasUseCase {
    suspend fun invoke(): List<Camera>
}

class GetListOfCamerasUseCaseImpl(
    private val cameraRepository: CameraRepository
) : GetListOfCamerasUseCase {
    override suspend fun invoke(): List<Camera> {
        return cameraRepository.getListOfCameras()
    }
}