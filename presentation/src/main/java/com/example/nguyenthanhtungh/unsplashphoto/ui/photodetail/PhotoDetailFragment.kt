package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentPhotoDetailBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.util.LEVEL_DOWNLOADABLE
import com.example.nguyenthanhtungh.unsplashphoto.util.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
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

        viewDataBinding.onDownloadClick = View.OnClickListener {
            requestPermission()
            if (viewModel.levelDownload.value == LEVEL_DOWNLOADABLE
                && viewModel.isPermissionGranted.value == true
            ) {
                viewModel.downloadPhoto()
            }
        }

        viewDataBinding.onBackPress = View.OnClickListener {
            onBackPress()
        }

        viewModel.apply {

            photoItem.value = arguments?.getParcelable(PHOTO_ITEM)

            levelDownload.observe(this@PhotoDetailFragment, Observer {
                viewDataBinding.photoDetailDownload.setImageLevel(it)
            })
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                context ?: return,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity ?: return,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            viewModel.isPermissionGranted.value = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                viewModel.isPermissionGranted.value =
                        grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            }
        }
    }
}
