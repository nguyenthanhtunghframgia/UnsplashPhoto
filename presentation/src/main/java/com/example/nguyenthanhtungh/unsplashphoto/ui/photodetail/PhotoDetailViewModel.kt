package com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.util.LEVEL_DOWNLOADABLE
import com.example.nguyenthanhtungh.unsplashphoto.util.LEVEL_DOWNLOADED
import java.io.File

class PhotoDetailViewModel : BaseViewModel() {
    val photoItem = MutableLiveData<PhotoItem>()
    val levelDownload = MutableLiveData<Int>()
    val isDownloading = MutableLiveData<Boolean>()
    val isPermissionGranted = MutableLiveData<Boolean>()
    var downLoadId = MutableLiveData<Long>()
    var downLoadStatus = MutableLiveData<Int>()
    var errorMessage = MutableLiveData<String>()

    fun checkDownloaded(path: String) {
        val file = File(path)
        if (file.exists()) {
            levelDownload.value = LEVEL_DOWNLOADED
            return
        }
        levelDownload.value = LEVEL_DOWNLOADABLE
    }
}
