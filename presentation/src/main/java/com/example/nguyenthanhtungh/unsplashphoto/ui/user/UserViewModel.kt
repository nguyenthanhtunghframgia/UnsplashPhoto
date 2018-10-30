package com.example.nguyenthanhtungh.unsplashphoto.ui.user

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.history.DeleteHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.GetHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.InsertHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.LimitHistoryUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItem
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserViewModel(
    private val historyItemMapper: HistoryItemMapper,
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val limitHistoryUseCase: LimitHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : BaseViewModel() {
    val errorMessage = MutableLiveData<String>()
    val isDelete = MutableLiveData<Boolean>()
    val listHistory = MutableLiveData<List<HistoryItem>>()

    fun getListHistory() {
        addDisposable(
            getHistoryUseCase.createObservable(GetHistoryUseCase.Param())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { listCollectionItem ->
                    listCollectionItem.map { historyItemMapper.mapToPresentation(it) }
                }
                .subscribe({
                    onLoadSuccess(it)
                }, { onLoadFail(it) })
        )
    }

    fun deleteHistory() {
        addDisposable(
            deleteHistoryUseCase.createObservable(DeleteHistoryUseCase.Param())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    getListHistory()
                }
                .subscribe({
                    isDelete.value = true
                }, { isDelete.value = false })
        )
    }

    private fun onLoadSuccess(list: List<HistoryItem>) {
        listHistory.value = list
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
