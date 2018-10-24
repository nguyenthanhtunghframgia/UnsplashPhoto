package com.example.nguyenthanhtungh.unsplashphoto.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentHomeViewModel(
    val collectionItemMapper: CollectionItemMapper,
    val collectionUseCase: CollectionUseCase
) : BaseViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isRefresh = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val listCollectionItem = MutableLiveData<List<CollectionItem>>()

    fun getListCollectionItem() {
        addDisposable(collectionUseCase.createObservable(CollectionUseCase.Param())
            .doOnSubscribe { isLoading.value = true }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                isLoading.value = false
                isRefresh.value = false
            }
            .map { listCollection ->
                listCollection.map { collectionItemMapper.mapToPresentation(it) }
            }
            .subscribe({
                onLoadSuccess(it)
            }, { onLoadFail(it) })
        )
    }

    fun refreshData() {
        getListCollectionItem()
    }

    private fun onLoadSuccess(list: List<CollectionItem>) {
        listCollectionItem.value = list
    }

    private fun onLoadFail(throwable: Throwable) {
        errorMessage.value = throwable.message
    }
}
