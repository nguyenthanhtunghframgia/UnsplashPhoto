package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem

class PhotoDetailViewModel : BaseViewModel() {
    val photoItem = MutableLiveData<PhotoItem>()
    val levelDownload = MutableLiveData<Int>().apply { value = 0 }
    val isDownloading = MutableLiveData<Boolean>()

    fun downloadPhoto() {
        isDownloading.value = true
        levelDownload.value = 1
    }
}
