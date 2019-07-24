package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.CollectionEntityMapper
import com.example.nguyenthanhtungh.data.model.HistoryEntityMapper
import com.example.nguyenthanhtungh.data.model.PhotoEntityMapper
import com.example.nguyenthanhtungh.data.source.local.dao.HistoryDao
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.example.nguyenthanhtungh.data.source.repository.CollectionRepositoryImpl
import com.example.nguyenthanhtungh.data.source.repository.HistoryRepositoryImpl
import com.example.nguyenthanhtungh.data.source.repository.PhotoRepositoryImpl
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.repository.HistoryRepository
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import org.koin.dsl.module.module

val repositoryModule = module(override = true) {
    single { createCollectionRepository(get(), get()) }
    single { createPhotoRepository(get(), get()) }
    single { createHistoryRepository(get(), get()) }
}

fun createCollectionRepository(
    apiService: ApiService,
    collectionEntityMapper: CollectionEntityMapper
): CollectionRepository = CollectionRepositoryImpl(apiService, collectionEntityMapper)

fun createPhotoRepository(
    apiService: ApiService,
    photoEntityMapper: PhotoEntityMapper
): PhotoRepository = PhotoRepositoryImpl(apiService, photoEntityMapper)

fun createHistoryRepository(
    historyDao: HistoryDao,
    historyEntityMapper: HistoryEntityMapper
): HistoryRepository = HistoryRepositoryImpl(historyDao, historyEntityMapper)
