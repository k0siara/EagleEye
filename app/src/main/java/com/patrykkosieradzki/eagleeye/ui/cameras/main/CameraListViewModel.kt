package com.patrykkosieradzki.eagleeye.ui.cameras.main

import com.hadilq.liveevent.LiveEvent
import com.patrykkosieradzki.eagleeye.domain.model.Camera

import com.patrykkosieradzki.eagleeye.domain.usecases.GetListOfCamerasUseCase
import com.patrykkosieradzki.eagleeye.ui.utils.BaseViewModel
import com.patrykkosieradzki.eagleeye.ui.utils.ViewState
import com.patrykkosieradzki.eagleeye.ui.utils.fireEvent

class CameraListViewModel(
    private val getListOfCamerasUseCase: GetListOfCamerasUseCase
) : BaseViewModel<CameraListViewState>(
    initialState = CameraListViewState(inProgress = true)
) {
    val goToCameraDetailsEvent = LiveEvent<Camera>()

    override fun initialize() {
        safeLaunch {
            val cameras = getListOfCamerasUseCase.invoke()
            updateViewState { it.copy(
                cameras = cameras,
                inProgress = false
            ) }
        }
    }

    fun onCameraItemClicked(item: Camera) {
        goToCameraDetailsEvent.fireEvent(item)
    }
}

data class CameraListViewState(
    override val inProgress: Boolean,
    val cameras: List<Camera> = emptyList()
) : ViewState {
    override fun toSuccess() = copy(inProgress = false)
}