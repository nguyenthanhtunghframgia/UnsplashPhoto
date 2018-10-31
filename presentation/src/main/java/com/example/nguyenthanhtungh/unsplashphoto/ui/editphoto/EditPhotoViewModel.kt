package com.example.nguyenthanhtungh.unsplashphoto.ui.editphoto

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem

class EditPhotoViewModel(

) : BaseViewModel() {
    val photoItem = MutableLiveData<PhotoItem>()
}