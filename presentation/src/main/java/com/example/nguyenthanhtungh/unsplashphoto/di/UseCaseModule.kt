package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.PhotoUseCase
import org.koin.dsl.module.module

val useCaseModule = module(override = true) {
    single { createPhotoUseCase(get()) }
    single { createCollectionUseCase(get()) }
}

fun createPhotoUseCase(photoRepository: PhotoRepository)
        = PhotoUseCase(photoRepository)

fun createCollectionUseCase(collectionRepository: CollectionRepository)
        = CollectionUseCase(collectionRepository)
