package com.example.nguyenthanhtungh.domain.usecase.history

import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single


open class DeleteHistoryUseCase(private val historyRepository: HistoryRepository) :
    UseCase<DeleteHistoryUseCase.Param, Single<Int>>() {

    override fun createObservable(param: Param?): Single<Int> {
        param?.let {
            return Single.defer {
                Single.just(historyRepository.deleteHistory())
            }
        }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param
}
