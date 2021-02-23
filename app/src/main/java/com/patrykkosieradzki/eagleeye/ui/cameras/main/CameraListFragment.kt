package com.patrykkosieradzki.eagleeye.ui.cameras.main

import android.view.View
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.databinding.LoginFragmentBinding
import com.patrykkosieradzki.eagleeye.ui.utils.BaseFragment

class CameraListFragment : BaseFragment<CameraListViewState, CameraListViewModel, LoginFragmentBinding>(
    R.layout.camera_list_fragment, CameraListViewModel::class
) {
    override fun setupViews(view: View) {
        super.setupViews(view)

    }
}