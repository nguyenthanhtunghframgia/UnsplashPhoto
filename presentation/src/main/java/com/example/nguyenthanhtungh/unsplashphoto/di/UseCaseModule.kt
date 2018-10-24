package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.collection.SearchCollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.PhotoUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.SearchPhotoUseCase
import org.koin.dsl.module.module

val useCaseModule = module(override = true) {
    single { createPhotoUseCase(get()) }
    single { createCollectionUseCase(get()) }
    single { createSearchCollectionUseCase(get()) }
    single { createSearchPhotoUseCase(get()) }
}

fun createPhotoUseCase(photoRepository: PhotoRepository)
        = PhotoUseCase(photoRepository)

fun createCollectionUseCase(collectionRepository: CollectionRepository)
        = CollectionUseCase(collectionRepository)

fun createSearchCollectionUseCase(collectionRepository: CollectionRepository)
        = SearchCollectionUseCase(collectionRepository)

fun createSearchPhotoUseCase(photoRepository: PhotoRepository)
        = SearchPhotoUseCase(photoRepository)
