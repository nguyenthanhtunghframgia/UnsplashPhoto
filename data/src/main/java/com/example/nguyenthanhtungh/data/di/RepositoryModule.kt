package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.*
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.example.nguyenthanhtungh.data.source.repository.CollectionRepositoryImpl
import com.example.nguyenthanhtungh.data.source.repository.PhotoRepositoryImpl
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import org.koin.dsl.module.module

val repositoryModule = module(override = true) {
    single { createCollectionRepository(get(), get()) }
    single { createPhotoRepository(get(), get()) }
}

fun createCollectionRepository(
    apiService: ApiService,
    collectionEntityMapper: CollectionEntityMapper
): CollectionRepository = CollectionRepositoryImpl(apiService, collectionEntityMapper)

fun createPhotoRepository(
    apiService: ApiService,
    photoEntityMapper: PhotoEntityMapper
): PhotoRepository = PhotoRepositoryImpl(apiService, photoEntityMapper)
