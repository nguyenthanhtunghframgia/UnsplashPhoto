package com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.photo.PhotoUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.rx.SchedulerProvider

class CollectionDetailViewModel(
    private val photoItemMapper: PhotoItemMapper,
    private val photoUseCase: PhotoUseCase,
    private val appSchedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isLoadMore = MutableLiveData<Boolean>()
    var isRefresh = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val collectionId = MutableLiveData<String>()
    val collectionTitle = MutableLiveData<String>()
    val listCollectionPhotoItem = MutableLiveData<List<PhotoItem>>()
    private var currentPage = MutableLiveData<Int>().apply { value = 0 }

    private fun getListCollectionPhotoItems(id: String, page: Int) {
        addDisposable(photoUseCase.createObservable(PhotoUseCase.Param(id, page))
            .subscribeOn(appSchedulerProvider.io())
            .observeOn(appSchedulerProvider.ui())
            .doAfterTerminate {
                isLoadMore.value = false
                isLoading.value = false
                isRefresh.value = false
            }
            .map { listCollectionItem ->
                listCollectionItem.map { photoItemMapper.mapToPresentation(it) }
            }
            .subscribe({
                onLoadSuccess(page, it)
            }, { onLoadFail(it) })
        )
    }

    private fun isFirst() = currentPage.value == 0
            && (listCollectionPhotoItem.value == null || listCollectionPhotoItem.value?.size == 0)

    fun firstLoad(id: String) {
        if (isFirst()) {
            isLoading.value = true
            getListCollectionPhotoItems(id, 1)
        }
    }

    fun refresh(id: String) {
        isRefresh.value = true
        getListCollectionPhotoItems(id, 1)
    }

    fun onLoadMore(id: String) {
        isLoadMore.value = true
        getListCollectionPhotoItems(id, currentPage.value?.plus(1) ?: 1)
    }

    private fun onLoadSuccess(page: Int, list: List<PhotoItem>) {
        currentPage.value = page

        if (isRefresh.value == true || isLoading.value == true) {
            listCollectionPhotoItem.value = list
            return
        }

        if (isLoadMore.value == true) {
            val listAdd = mutableListOf<PhotoItem>()
            listCollectionPhotoItem.value?.let {
                listAdd.addAll(it)
            }
            listAdd.addAll(list)
            listCollectionPhotoItem.value = listAdd
            return
        }
    }

    private fun onLoadFail(throwable: Throwable) {
        errorMessage.value = throwable.message
    }
}
