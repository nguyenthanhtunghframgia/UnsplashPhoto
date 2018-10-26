package com.example.nguyenthanhtungh.domain.usecase.history

import com.example.nguyenthanhtungh.domain.model.History
import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

class GetHistoryUseCase(private val historyRepository: HistoryRepository) :
    UseCase<GetHistoryUseCase.Param, Single<List<History>>>() {

    override fun createObservable(param: Param?): Single<List<History>> {
        param?.let { return historyRepository.getHistory() }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param(val page: Int)
}
