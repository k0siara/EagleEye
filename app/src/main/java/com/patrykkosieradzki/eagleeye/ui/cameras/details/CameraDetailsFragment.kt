package com.patrykkosieradzki.eagleeye.ui.cameras.details

import android.view.View
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.databinding.CameraDetailsFragmentBinding
import com.patrykkosieradzki.eagleeye.ui.utils.BaseFragment

class CameraDetailsFragment : BaseFragment<CameraDetailsViewState, CameraDetailsViewModel, CameraDetailsFragmentBinding>(
    R.layout.camera_list_fragment, CameraDetailsViewModel::class
) {
    override fun setupViews(view: View) {
        super.setupViews(view)

    }
}