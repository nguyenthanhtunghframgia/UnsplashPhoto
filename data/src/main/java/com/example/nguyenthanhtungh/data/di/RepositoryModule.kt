package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.*
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.example.nguyenthanhtungh.data.source.repository.CollectionRepositoryImpl
import com.example.nguyenthanhtungh.data.source.repository.PhotoRepositoryImpl
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import org.koin.dsl.module.module

val repositoryModule = module(override = true) {
    single { createUrlsEntityMapper() }
    single { createUserEntityMapper() }
    single { createCoverPhotoEntityMapper(get()) }
    single { createCollectionEntityMapper(get()) }
    single { createPhotoEntityMapper(get(),get()) }

    single { createCollectionRepositoryImpl(get(), get()) }
    single { createPhotoRepositoryImpl(get(), get()) }
    single { createCollectionRepository(get()) }
    single { createPhotoRepository(get()) }
}

fun createUrlsEntityMapper() = UrlsEntityMapper()

fun createUserEntityMapper() = UserEntityMapper()

fun createCoverPhotoEntityMapper(urlsEntityMapper: UrlsEntityMapper)
        = CoverPhotoEntityMapper(urlsEntityMapper)

fun createCollectionEntityMapper(coverPhotoEntityMapper: CoverPhotoEntityMapper)
        = CollectionEntityMapper(coverPhotoEntityMapper)

fun createPhotoEntityMapper(userEntityMapper: UserEntityMapper, urlsEntityMapper: UrlsEntityMapper)
        = PhotoEntityMapper(userEntityMapper, urlsEntityMapper)

fun createCollectionRepositoryImpl(apiService: ApiService,
                               collectionEntityMapper: CollectionEntityMapper)
        = CollectionRepositoryImpl(apiService, collectionEntityMapper)

fun createPhotoRepositoryImpl(apiService: ApiService,
                               photoEntityMapper: PhotoEntityMapper)
        = PhotoRepositoryImpl(apiService, photoEntityMapper)

fun createCollectionRepository(collectionRepositoryImpl: CollectionRepositoryImpl) : CollectionRepository
        = collectionRepositoryImpl

fun createPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl) : PhotoRepository
        = photoRepositoryImpl
