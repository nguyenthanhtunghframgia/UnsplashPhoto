package com.example.nguyenthanhtungh.data.source.repository

import com.example.nguyenthanhtungh.data.model.HistoryEntityMapper
import com.example.nguyenthanhtungh.data.source.local.dao.HistoryDao
import com.example.nguyenthanhtungh.domain.model.History
import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import io.reactivex.Single

class HistoryRepositoryImpl(
    private val historyDao: HistoryDao,
    private val historyEntityMapper: HistoryEntityMapper
) : HistoryRepository {

    override fun getHistory(): Single<List<History>> {
        return historyDao.getHistory().map { listHistory ->
            listHistory.map { historyEntityMapper.mapToDomain(it) }
        }
    }

    override fun insertHistory(history: History) {
        return historyDao.insertHistory(historyEntityMapper.mapToEntity(history))
    }

    override fun deleteHistory() {
        return historyDao.deleteAll()
    }
}
