package com.example.nguyenthanhtungh.unsplashphoto.ui.user

import androidx.lifecycle.MutableLiveData
import com.example.nguyenthanhtungh.domain.usecase.history.DeleteHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.GetHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.InsertHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.LimitHistoryUseCase
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseViewModel
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItem
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.rx.SchedulerProvider

class UserViewModel(
    private val historyItemMapper: HistoryItemMapper,
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val limitHistoryUseCase: LimitHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val appSchedulerProvider: SchedulerProvider
) : BaseViewModel() {
    val errorMessage = MutableLiveData<String>()
    val errorInsertMessage = MutableLiveData<String>()
    val isDelete = MutableLiveData<Boolean>()
    val isEmpty = MutableLiveData<Boolean>()
    val isInsertComplete = MutableLiveData<Boolean>()
    val listHistory = MutableLiveData<List<HistoryItem>>()

    fun getListHistory() {
        addDisposable(
            getHistoryUseCase.createObservable(GetHistoryUseCase.Param())
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
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
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
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
        if (list.isEmpty()) {
            isEmpty.value = true
            return
        }
        isEmpty.value = false
    }

    private fun onLoadFail(throwable: Throwable) {
        isEmpty.value = true
        errorMessage.value = throwable.message
    }

    fun insertHistory(query: String) {
        addDisposable(
            insertHistoryUseCase.createObservable(InsertHistoryUseCase.Param(query))
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .subscribe({
                    isInsertComplete.value = true
                }, {
                    isInsertComplete.value = false
                    errorInsertMessage.value = it.message
                })
        )
        addDisposable(
            limitHistoryUseCase.createObservable(LimitHistoryUseCase.Param())
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .subscribe({
                    isInsertComplete.value = true
                }, {
                    isInsertComplete.value = false
                    errorInsertMessage.value = it.message
                })
        )
    }
}
