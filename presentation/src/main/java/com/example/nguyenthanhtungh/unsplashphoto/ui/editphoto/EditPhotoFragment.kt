package com.example.nguyenthanhtungh.unsplashphoto.ui.editphoto

import android.os.Bundle
import android.view.View
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentEditPhotoBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import org.koin.android.viewmodel.ext.android.viewModel

class EditPhotoFragment : BaseFragment<FragmentEditPhotoBinding, EditPhotoViewModel>() {

    companion object {
        const val PHOTO_ITEM = "PHOTO_ITEM"
        const val TAG = "EditPhotoFragment"
        fun newInstance(photoItem: PhotoItem) = EditPhotoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PHOTO_ITEM, photoItem)
            }
        }
    }

    override val bindingVariable: Int = BR.editPhotoViewModel

    override val viewModel by viewModel<EditPhotoViewModel>()

    override val layoutId: Int = R.layout.fragment_edit_photo

    override fun initComponent(viewDataBinding: FragmentEditPhotoBinding) {

        hideBottomView()

        viewModel.photoItem.value = arguments?.getParcelable(PHOTO_ITEM)

        viewDataBinding.onBackPress = View.OnClickListener {
            onBackPress()
        }
    }
}
