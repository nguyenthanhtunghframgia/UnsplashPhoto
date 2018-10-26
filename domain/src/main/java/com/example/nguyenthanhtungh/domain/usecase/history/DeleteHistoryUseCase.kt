package com.example.nguyenthanhtungh.domain.usecase.history

import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase

class DeleteHistoryUseCase(private val historyRepository: HistoryRepository) :
    UseCase<DeleteHistoryUseCase.Param, Unit>() {

    override fun createObservable(param: Param?): Unit {
        param?.let { return historyRepository.deleteHistory() }
    }

    override fun onCleared() {
    }

    class Param()
}
