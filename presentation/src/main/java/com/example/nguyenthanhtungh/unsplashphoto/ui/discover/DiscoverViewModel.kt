package com.example.nguyenthanhtungh.unsplashphoto.ui.discover

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.history.InsertHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.LimitHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.DiscoverPhotoUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItem
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DiscoverViewModel(
    private val photoItemMapper: PhotoItemMapper,
    private val historyItemMapper: HistoryItemMapper,
    private val discoverPhotoUseCase: DiscoverPhotoUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val limitHistoryUseCase: LimitHistoryUseCase
) : BaseViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isLoadMore = MutableLiveData<Boolean>()
    var isRefresh = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val listDiscoverPhotoItem = MutableLiveData<List<PhotoItem>>()
    private var currentPage = MutableLiveData<Int>().apply { value = 0 }

    private fun getListDiscoverPhotoItems(page: Int) {
        addDisposable(discoverPhotoUseCase.createObservable(DiscoverPhotoUseCase.Param(page))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
            && (listDiscoverPhotoItem.value == null || listDiscoverPhotoItem.value?.size == 0)

    fun firstLoad() {
        if (isFirst()) {
            isLoading.value = true
            getListDiscoverPhotoItems(1)
        }
    }

    fun refresh() {
        isRefresh.value = true
        getListDiscoverPhotoItems(1)
    }

    fun onLoadMore() {
        isLoadMore.value = true
        getListDiscoverPhotoItems(currentPage.value?.plus(1) ?: 1)
    }

    private fun onLoadSuccess(page: Int, list: List<PhotoItem>) {
        currentPage.value = page

        if (isRefresh.value == true || isLoading.value == true) {
            listDiscoverPhotoItem.value = list
            return
        }

        if (isLoadMore.value == true) {
            val listAdd = mutableListOf<PhotoItem>()
            listDiscoverPhotoItem.value?.let {
                listAdd.addAll(it)
            }
            listAdd.addAll(list)
            listDiscoverPhotoItem.value = listAdd
            return
        }
    }

    private fun onLoadFail(throwable: Throwable) {
        errorMessage.value = throwable.message
    }

    fun insertHistory(historyItem: HistoryItem) {
        addDisposable(
            insertHistoryUseCase.createObservable(
                InsertHistoryUseCase.Param(
                    historyItemMapper.mapToDomain(historyItem)
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
        addDisposable(
            limitHistoryUseCase.createObservable(LimitHistoryUseCase.Param())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }
}
