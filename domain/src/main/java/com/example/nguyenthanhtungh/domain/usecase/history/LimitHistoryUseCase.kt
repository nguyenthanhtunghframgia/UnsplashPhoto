package com.example.nguyenthanhtungh.domain.usecase.history

import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

class LimitHistoryUseCase(private val historyRepository: HistoryRepository) :
    UseCase<LimitHistoryUseCase.Param, Single<Int>>() {

    override fun createObservable(param: Param?): Single<Int> {
        param?.let {
            return Single.defer {
                Single.just(historyRepository.limitRecord())
            }
        }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param()
}
