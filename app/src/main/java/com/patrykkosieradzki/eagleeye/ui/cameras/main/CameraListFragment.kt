package com.patrykkosieradzki.eagleeye.ui.cameras.main

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.patrykkosieradzki.eagleeye.BR
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.databinding.CameraListFragmentBinding
import com.patrykkosieradzki.eagleeye.domain.model.Camera
import com.patrykkosieradzki.eagleeye.ui.utils.BaseFragment
import com.patrykkosieradzki.eagleeye.ui.utils.OnItemClickListener
import com.patrykkosieradzki.eagleeye.ui.utils.navigateTo
import me.tatarka.bindingcollectionadapter2.ItemBinding

class CameraListFragment : BaseFragment<CameraListViewState, CameraListViewModel, CameraListFragmentBinding>(
    R.layout.camera_list_fragment, CameraListViewModel::class
) {
    private val itemBinding = ItemBinding.of<Camera>(BR.item, R.layout.camera_item_layout)
        .bindExtra(BR.listener, object : OnItemClickListener<Camera> {
            override fun onClick(item: Camera) {
                viewModel.onCameraItemClicked(item)
            }
        })

    override fun setupViews(view: View) {
        super.setupViews(view)
        with(binding) {
            itemBinding = this@CameraListFragment.itemBinding
            val dividerDecoration = DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
            recyclerView.addItemDecoration(dividerDecoration)
        }
        with(viewModel) {
            goToCameraDetailsEvent.observe(viewLifecycleOwner) {
                val directions = CameraListFragmentDirections.actionCameraListFragmentToCameraDetailsFragment(it.id)
                navigateTo(directions)
            }
        }
    }
}