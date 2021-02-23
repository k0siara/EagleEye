package com.patrykkosieradzki.eagleeye.ui.cameras.details

import android.view.View
import androidx.navigation.fragment.navArgs
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.databinding.CameraDetailsFragmentBinding
import com.patrykkosieradzki.eagleeye.ui.utils.BaseFragment

class CameraDetailsFragment : BaseFragment<CameraDetailsViewState, CameraDetailsViewModel, CameraDetailsFragmentBinding>(
    R.layout.camera_details_fragment, CameraDetailsViewModel::class
) {
    private val args: CameraDetailsFragmentArgs by navArgs()

    override fun setupViews(view: View) {
        super.setupViews(view)
        with(viewModel) {
            loadCameraDetails(args.cameraId)
        }
    }
}