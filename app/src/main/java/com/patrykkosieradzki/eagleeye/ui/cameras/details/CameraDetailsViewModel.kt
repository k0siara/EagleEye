package com.patrykkosieradzki.eagleeye.ui.cameras.details

import com.patrykkosieradzki.eagleeye.domain.model.CameraDetails
import com.patrykkosieradzki.eagleeye.domain.model.CameraSettings
import com.patrykkosieradzki.eagleeye.domain.usecases.GetCameraDetailsUseCase
import com.patrykkosieradzki.eagleeye.ui.cameras.details.CameraDetailsViewModel.Companion.INITIAL_CAMERA_DETAILS
import com.patrykkosieradzki.eagleeye.ui.utils.BaseViewModel
import com.patrykkosieradzki.eagleeye.ui.utils.ViewState

class CameraDetailsViewModel(
    private val getCameraDetailsUseCase: GetCameraDetailsUseCase
) : BaseViewModel<CameraDetailsViewState>(
    initialState = CameraDetailsViewState(inProgress = true)
) {

    fun loadCameraDetails(cameraId: String) {
        safeLaunch {
            val cameraDetails = getCameraDetailsUseCase.invoke(cameraId)
            updateViewState { it.copy(
                cameraDetails = cameraDetails,
                inProgress = false
            ) }
        }
    }

    companion object {
        val INITIAL_CAMERA_DETAILS = CameraDetails(
            "", "", "", emptyList(), CameraSettings("", "", "")
        )
    }
}

data class CameraDetailsViewState(
    override val inProgress: Boolean,
    val cameraDetails: CameraDetails = INITIAL_CAMERA_DETAILS
) : ViewState {
    override fun toSuccess() = copy(inProgress = false)
}