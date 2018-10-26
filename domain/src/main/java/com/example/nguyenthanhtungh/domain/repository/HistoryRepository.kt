package com.example.nguyenthanhtungh.domain.repository

import com.example.nguyenthanhtungh.domain.model.History
import io.reactivex.Single

interface HistoryRepository {
    fun getHistory(): Single<List<History>>

    fun insertHistory(history: History)

    fun deleteHistory()
}
