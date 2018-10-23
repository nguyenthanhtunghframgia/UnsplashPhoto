package com.example.data.di

import com.example.nguyenthanhtungh.data.model.*
import com.example.nguyenthanhtungh.data.source.local.CollectionLocalDataSource
import com.example.nguyenthanhtungh.data.source.local.PhotoLocalDataSource
import com.example.nguyenthanhtungh.data.source.remote.CollectionRemoteDataSource
import com.example.nguyenthanhtungh.data.source.remote.PhotoRemoteDataSource
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.example.nguyenthanhtungh.data.source.repository.CollectionRepositoryImpl
import com.example.nguyenthanhtungh.data.source.repository.PhotoRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module(override = true) {
    single { createCollectionLocalDataSource() }
    single { createPhotoLocalDataSource() }
    single { createCollectionRemoteDataSource(get()) }
    single { createPhotoRemoteDataSource(get()) }
    single { createUrlsEntityMapper() }
    single { createUserEntityMapper() }
    single { createCoverPhotoEntityMapper(get()) }
    single { createCollectionEntityMapper(get()) }
    single { createPhotoEntityMapper(get(),get()) }
    single { createCollectionRepository(get(), get(), get()) }
    single { createPhotoRepository(get(), get(), get()) }
}

fun createCollectionLocalDataSource()
        = CollectionLocalDataSource()

fun createPhotoLocalDataSource()
        = PhotoLocalDataSource()

fun createCollectionRemoteDataSource(apiService: ApiService)
        = CollectionRemoteDataSource(apiService)

fun createPhotoRemoteDataSource(apiService: ApiService)
        = PhotoRemoteDataSource(apiService)

fun createUrlsEntityMapper() = UrlsEntityMapper()

fun createUserEntityMapper() = UserEntityMapper()

fun createCoverPhotoEntityMapper(urlsEntityMapper: UrlsEntityMapper)
        = CoverPhotoEntityMapper(urlsEntityMapper)

fun createCollectionEntityMapper(coverPhotoEntityMapper: CoverPhotoEntityMapper)
        = CollectionEntityMapper(coverPhotoEntityMapper)

fun createPhotoEntityMapper(userEntityMapper: UserEntityMapper, urlsEntityMapper: UrlsEntityMapper)
        = PhotoEntityMapper(userEntityMapper, urlsEntityMapper)

fun createCollectionRepository(collectionRemoteDataSource: CollectionRemoteDataSource,
                               collectionLocalDataSource: CollectionLocalDataSource,
                               collectionEntityMapper: CollectionEntityMapper)
        = CollectionRepositoryImpl(collectionRemoteDataSource, collectionLocalDataSource, collectionEntityMapper)

fun createPhotoRepository(photoRemoteDataSource: PhotoRemoteDataSource,
                               photoLocalDataSource: PhotoLocalDataSource,
                               photoEntityMapper: PhotoEntityMapper)
        = PhotoRepositoryImpl(photoRemoteDataSource, photoLocalDataSource, photoEntityMapper)
