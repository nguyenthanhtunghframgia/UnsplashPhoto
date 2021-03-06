package com.example.nguyenthanhtungh.unsplashphoto.ui.search

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.collection.SearchCollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.SearchPhotoUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.rx.SchedulerProvider

class SearchViewModel(
    private val searchPhotoUseCase: SearchPhotoUseCase,
    private val photoItemMapper: PhotoItemMapper,
    private val searchCollectionUseCase: SearchCollectionUseCase,
    private val collectionItemMapper: CollectionItemMapper,
    private val appSchedulerProvider: SchedulerProvider
) : BaseViewModel() {
    val queryString = MutableLiveData<String>()
    val isLoadingPhoto = MutableLiveData<Boolean>()
    private val isLoadingCollection = MutableLiveData<Boolean>()
    val isRefresh = MutableLiveData<Boolean>()
    val isLoadMore = MutableLiveData<Boolean>()
    val isCollectionEmpty = MutableLiveData<Boolean>()
    val isPhotoEmpty = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private val currentPage = MutableLiveData<Int>().apply { value = 0 }
    val listSearchCollection = MutableLiveData<List<CollectionItem>>()
    val listSearchPhotos = MutableLiveData<List<PhotoItem>>()

    private fun getListSearchCollection(query: String, page: Int) {
        addDisposable(searchCollectionUseCase.createObservable(SearchCollectionUseCase.Param(query, page))
            .subscribeOn(appSchedulerProvider.io())
            .observeOn(appSchedulerProvider.ui())
            .doAfterTerminate {
                isLoadingCollection.value = false
            }
            .map { listCollectionItem ->
                listCollectionItem.map { collectionItemMapper.mapToPresentation(it) }
            }
            .subscribe({
                listSearchCollection.value = it
                isCollectionEmpty.value = it.isEmpty()
            }, { onLoadFail(it) })
        )
    }

    private fun getListSearchPhoto(query: String, page: Int) {
        addDisposable(searchPhotoUseCase.createObservable(SearchPhotoUseCase.Param(query, page))
            .subscribeOn(appSchedulerProvider.io())
            .observeOn(appSchedulerProvider.ui())
            .doAfterTerminate {
                isLoadMore.value = false
                isLoadingPhoto.value = false
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
            && (listSearchPhotos.value == null || listSearchPhotos.value?.size == 0)
            || (listSearchCollection.value == null || listSearchCollection.value?.size == 0)

    fun firstLoad(query: String) {
        if (isFirst()) {
            isLoadingPhoto.value = true
            isLoadingCollection.value = true
            getListSearchCollection(query, 1)
            getListSearchPhoto(query, 1)
        }
    }

    fun refresh(query: String) {
        isRefresh.value = true
        getListSearchPhoto(query, 1)
    }

    fun onLoadMore(query: String) {
        isLoadMore.value = true
        getListSearchPhoto(query, currentPage.value?.plus(1) ?: 1)
    }

    private fun onLoadSuccess(page: Int, list: List<PhotoItem>) {
        if (page == 1 && list.isEmpty()) {
            isPhotoEmpty.value = true
            return
        }
        currentPage.value = page

        if (isRefresh.value == true || isLoadingPhoto.value == true) {
            listSearchPhotos.value = list
            return
        }

        if (isLoadMore.value == true) {
            val listAdd = mutableListOf<PhotoItem>()
            listSearchPhotos.value?.let {
                listAdd.addAll(it)
            }
            listAdd.addAll(list)
            listSearchPhotos.value = listAdd
            return
        }
    }

    private fun onLoadFail(throwable: Throwable) {
        errorMessage.value = throwable.message
    }
}
