package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentPhotoDetailBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.util.LEVEL_DOWNLOADABLE
import org.koin.android.viewmodel.ext.android.viewModel

class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding, PhotoDetailViewModel>() {

    companion object {
        const val PHOTO_ITEM = "PHOTO_ITEM"
        const val TAG = "PhotoDetailFragment"
        fun newInstance(photoItem: PhotoItem) = PhotoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PHOTO_ITEM, photoItem)
            }
        }
    }

    override val bindingVariable: Int = BR.photoDetail

    override val viewModel by viewModel<PhotoDetailViewModel>()

    override val layoutId: Int = R.layout.fragment_photo_detail

    override fun initComponent(viewDataBinding: FragmentPhotoDetailBinding) {

        val photoItem = arguments?.getParcelable<PhotoItem>(PHOTO_ITEM)

        viewDataBinding.onDownloadClick = View.OnClickListener {
            if (viewModel.levelDownload.value == LEVEL_DOWNLOADABLE) {
                viewModel.downloadPhoto()
            }
        }

        viewDataBinding.onBackPress = View.OnClickListener {
            onBackPress()
        }

        arguments?.apply {
            getParcelable<PhotoItem>(PHOTO_ITEM)?.apply {
                viewModel.photoItem.value = this
            }
        }

        viewModel.apply {
            levelDownload.observe(this@PhotoDetailFragment, Observer {
                viewDataBinding.photoDetailDownload.setImageLevel(it)
            })
        }
    }
}
