package com.example.nguyenthanhtungh.unsplashphoto.ui.collection

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CollectionViewModel(
    private val collectionItemMapper: CollectionItemMapper,
    private val collectionUseCase: CollectionUseCase
) : BaseViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isLoadMore = MutableLiveData<Boolean>()
    var isRefresh = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val listCollectionItem = MutableLiveData<List<CollectionItem>>()
    private var currentPage = MutableLiveData<Int>().apply { value = 0 }

    private fun getListCollectionItem(page: Int) {
        addDisposable(collectionUseCase.createObservable(CollectionUseCase.Param(page))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                isLoadMore.value = false
                isLoading.value = false
                isRefresh.value = false
            }
            .map { listCollection ->
                listCollection.map { collectionItemMapper.mapToPresentation(it) }
            }
            .subscribe({
                onLoadSuccess(page, it)
            }, { onLoadFail(it) })
        )
    }

    private fun isFirst() = currentPage.value == 0
            && (listCollectionItem.value == null || listCollectionItem.value?.size == 0)

    fun firstLoad() {
        if (isFirst()) {
            isLoading.value = true
            getListCollectionItem(1)
        }
    }

    fun refreshData() {
        isRefresh.value = true
        getListCollectionItem(1)
    }

    fun onLoadMore() {
        isLoadMore.value = true
        getListCollectionItem(currentPage.value?.plus(1) ?: 1)
    }

    private fun onLoadSuccess(page: Int, list: List<CollectionItem>) {
        currentPage.value = page

        if (isRefresh.value == true || isLoading.value == true) {
            listCollectionItem.value = list
            return
        }

        if (isLoadMore.value == true) {
            val listAdd = mutableListOf<CollectionItem>()
            listCollectionItem.value?.let {
                listAdd.addAll(it)
            }
            listAdd.addAll(list)
            listCollectionItem.value = listAdd
            return
        }
    }

    private fun onLoadFail(throwable: Throwable) {
        errorMessage.value = throwable.message
    }
}
