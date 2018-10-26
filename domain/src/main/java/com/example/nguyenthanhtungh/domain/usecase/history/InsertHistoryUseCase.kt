package com.example.nguyenthanhtungh.domain.usecase.history

import com.example.nguyenthanhtungh.domain.model.History
import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase

class InsertHistoryUseCase(private val historyRepository: HistoryRepository) :
    UseCase<InsertHistoryUseCase.Param, Unit>() {

    override fun createObservable(param: Param?): Unit {
        param?.let { return historyRepository.insertHistory(param.history) }
    }

    override fun onCleared() {
    }

    class Param(val history: History)
}
