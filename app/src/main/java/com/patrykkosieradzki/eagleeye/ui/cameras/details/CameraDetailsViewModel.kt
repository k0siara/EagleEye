package com.patrykkosieradzki.eagleeye.ui.cameras.details

import com.patrykkosieradzki.eagleeye.ui.utils.BaseViewModel
import com.patrykkosieradzki.eagleeye.ui.utils.ViewState

class CameraDetailsViewModel(

) : BaseViewModel<CameraDetailsViewState>(
    initialState = CameraDetailsViewState(inProgress = true)
) {
    override fun initialize() {

    }
}

data class CameraDetailsViewState(
    override val inProgress: Boolean
) : ViewState {
    override fun toSuccess() = copy(inProgress = false)
}