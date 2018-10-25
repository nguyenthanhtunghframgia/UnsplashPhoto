package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem

class PhotoDetailViewModel : BaseViewModel() {
    val photoItem = MutableLiveData<PhotoItem>()
    val levelDownload = MutableLiveData<Int>()
    val isDownloading = MutableLiveData<Boolean>()


    fun checkDownloaded() {
        //todo
    }


    fun downloadPhoto() {
        //todo
    }
}
