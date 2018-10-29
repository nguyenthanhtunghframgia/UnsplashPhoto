package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentPhotoDetailBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.util.*
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

    private lateinit var downloadManager: DownloadManager

    override val bindingVariable: Int = BR.photoDetail

    override val viewModel by viewModel<PhotoDetailViewModel>()

    override val layoutId: Int = R.layout.fragment_photo_detail

    override fun initComponent(viewDataBinding: FragmentPhotoDetailBinding) {

        downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        viewModel.apply {

            photoItem.value = arguments?.getParcelable(PHOTO_ITEM)

            levelDownload.observe(this@PhotoDetailFragment, Observer {
                viewDataBinding.photoDetailDownload.setImageLevel(it)
            })
        }

        viewDataBinding.onDownloadClick = View.OnClickListener {
            requestPermission()
            if (viewModel.levelDownload.value == LEVEL_DOWNLOADABLE
                && viewModel.isPermissionGranted.value == true
            ) {
                viewModel.apply {
                    downloadPhoto(
                        photoItem.value?.urls?.full ?: return@OnClickListener
                    )

                    errorMessage.observe(this@PhotoDetailFragment, Observer {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        isDownloading.value = false
                        levelDownload.value = LEVEL_DOWNLOADABLE
                    })
                }
            }
        }

        viewDataBinding.onBackPress = View.OnClickListener {
            onBackPress()
        }

        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        context?.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val broadCastId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (broadCastId == viewModel.downLoadId.value) {
                    viewModel.apply {
                        getDownloadStatus()
                        downLoadStatus.observe(this@PhotoDetailFragment, Observer {
                            when (it) {
                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    levelDownload.value = LEVEL_DOWNLOADED
                                    DialogUtils.showToast(context, getString(R.string.download_success))
                                }
                                else ->
                                    DialogUtils.showToast(context, getString(R.string.download_fail))
                            }
                        })
                    }
                }
            }
        }, intentFilter)
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                context ?: return,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
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

    private fun downloadPhoto(link: String) {
        try {
            viewModel.isDownloading.value = true
            val uri = Uri.parse(link)
            val request = DownloadManager.Request(uri)
            request.apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                setAllowedOverRoaming(false)
                setTitle(viewModel.photoItem.value?.id)
                setDescription(viewModel.photoItem.value?.description)
                setVisibleInDownloadsUi(true)
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, viewModel.photoItem.value?.description.plus(IMAGE_EXTEND)
                )
            }
            viewModel.downLoadId.value = downloadManager.enqueue(request)

        } catch (exception: IllegalStateException) {
            viewModel.errorMessage.value = exception.message
        }
    }

    private fun getDownloadStatus() {
        viewModel.apply {
            val query = DownloadManager.Query()
            query.setFilterById(downLoadId.value ?: -1)
            val cursor = downloadManager.query(query)
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                downLoadStatus.value = cursor.getInt(columnIndex)
            } else {
                downLoadStatus.value = DownloadManager.ERROR_UNKNOWN
            }
            isDownloading.value = false
        }
    }

    private fun cancelDownLoad() {
        viewModel.apply {
            downloadManager.remove(downLoadId.value ?: -1)
            isDownloading.value = false
            levelDownload.value = LEVEL_DOWNLOADABLE

        }
    }
}
