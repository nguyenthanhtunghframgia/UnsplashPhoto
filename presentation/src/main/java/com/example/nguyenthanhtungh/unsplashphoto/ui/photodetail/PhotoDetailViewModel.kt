package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.util.LEVEL_DOWNLOADABLE

class PhotoDetailViewModel : BaseViewModel() {
    val photoItem = MutableLiveData<PhotoItem>()
    val levelDownload = MutableLiveData<Int>().apply { value = LEVEL_DOWNLOADABLE }
    val isDownloading = MutableLiveData<Boolean>()
    val isPermissionGranted = MutableLiveData<Boolean>()
    var downLoadId = MutableLiveData<Long>()
    var downLoadStatus = MutableLiveData<Int>()
    var errorMessage = MutableLiveData<String>()

    fun checkDownloaded() {
        //todo
    }
}
