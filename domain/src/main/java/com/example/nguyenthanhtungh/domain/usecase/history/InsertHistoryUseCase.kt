package com.example.nguyenthanhtungh.domain.usecase.history

import com.example.nguyenthanhtungh.domain.model.History
import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

open class InsertHistoryUseCase(private val historyRepository: HistoryRepository) :
    UseCase<InsertHistoryUseCase.Param, Single<Long>>() {

    override fun createObservable(param: Param?): Single<Long> {
        param?.let {
            return Single.defer {
                Single.just(historyRepository.insertHistory(History(1, param.query)))
            }
        }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param(val query: String)
}
