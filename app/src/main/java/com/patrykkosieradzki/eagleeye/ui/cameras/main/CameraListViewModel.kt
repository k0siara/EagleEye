package com.patrykkosieradzki.eagleeye.ui.cameras.main

import com.patrykkosieradzki.eagleeye.ui.utils.BaseViewModel
import com.patrykkosieradzki.eagleeye.ui.utils.ViewState

class CameraListViewModel(

) : BaseViewModel<CameraListViewState>(
    initialState = CameraListViewState(inProgress = true)
) {
    override fun initialize() {
        updateViewState { it.toSuccess() }
    }
}

data class CameraListViewState(
    override val inProgress: Boolean
) : ViewState {
    override fun toSuccess() = copy(inProgress = false)
}