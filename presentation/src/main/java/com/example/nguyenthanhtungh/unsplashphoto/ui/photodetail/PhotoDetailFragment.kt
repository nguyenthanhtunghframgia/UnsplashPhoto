package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import android.Manifest
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentPhotoDetailBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.editphoto.EditPhotoFragment
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

        hideBottomView()

        createNotificationChannel()

        downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        viewModel.apply {

            photoItem.value = arguments?.getParcelable(PHOTO_ITEM)

            checkDownloaded(
                StringUtils.getDownloadPath()
                    .plus(photoItem.value?.id.plus(IMAGE_EXTEND))
            )

            levelDownload.observe(viewLifecycleOwner, Observer {
                viewDataBinding.photoDetailDownload.setImageLevel(it)
            })
        }

        viewDataBinding.onDownloadClick = View.OnClickListener {
            if (viewModel.levelDownload.value == LEVEL_DOWNLOADABLE) {
                requestPermission()
                viewModel.apply {
                    isPermissionGranted.observe(viewLifecycleOwner, Observer {
                        when (isPermissionGranted.value) {
                            true -> downloadPhoto(
                                photoItem.value?.urls?.full ?: return@Observer
                            )
                        }
                    })

                    errorMessage.observe(viewLifecycleOwner, Observer {
                        DialogUtils.showToast(context, it)
                        isDownloading.value = false
                        levelDownload.value = LEVEL_DOWNLOADABLE
                    })
                }
            } else {
                DialogUtils.showToast(context, getString(R.string.photo_downloaded))
            }
        }

        viewDataBinding.onBackPress = View.OnClickListener {
            onBackPress()
        }

        viewDataBinding.onEditClick = View.OnClickListener {
            showEditFragment(viewModel.photoItem.value ?: return@OnClickListener)
        }

        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        context?.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val broadCastId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (broadCastId == viewModel.downLoadId.value) {
                    viewModel.apply {
                        getDownloadStatus()
                        downLoadStatus.observe(viewLifecycleOwner, Observer {
                            when (it) {
                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    levelDownload.value = LEVEL_DOWNLOADED
                                    DialogUtils.showToast(context, getString(R.string.download_success))
                                    showNotification(viewModel.photoItem.value)
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

    private fun showNotification(photoItem: PhotoItem?) {
        val builder = NotificationCompat.Builder(context ?: return, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download_purple)
            .setContentTitle(photoItem?.description)
            .setContentText(getString(R.string.download_success))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context ?: return)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
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
                    Environment.DIRECTORY_DOWNLOADS, viewModel.photoItem.value?.id.plus(IMAGE_EXTEND)
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

    private fun showEditFragment(photoItem: PhotoItem) {
        val editPhotoFragment = EditPhotoFragment.newInstance(photoItem)
        replaceFragment(
            R.id.frame_layout, editPhotoFragment, EditPhotoFragment.TAG, true
        )
    }
}
