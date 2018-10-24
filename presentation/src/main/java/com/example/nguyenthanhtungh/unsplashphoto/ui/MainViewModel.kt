package com.example.nguyenthanhtungh.unsplashphoto.ui

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.model.CoverPhotoItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.UrlsItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.UserItemMapper

class MainViewModel(
    val userItemMapper: UserItemMapper,
    val urlsItemMapper: UrlsItemMapper,
    val coverPhotoItemMapper: CoverPhotoItemMapper,
    val collectionUseCase: CollectionUseCase
) : BaseViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    val listCollection = MutableLiveData<List<CollectionItem>>()

    fun getListCollectionItem() {
    }
}
