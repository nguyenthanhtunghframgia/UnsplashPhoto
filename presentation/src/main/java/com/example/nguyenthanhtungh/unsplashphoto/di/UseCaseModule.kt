package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.collection.SearchCollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.DeleteHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.GetHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.InsertHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.DiscoverPhotoUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.PhotoUseCase
import com.example.nguyenthanhtungh.domain.usecase.photo.SearchPhotoUseCase
import org.koin.dsl.module.module

val useCaseModule = module(override = true) {
    single { PhotoUseCase(get()) }
    single { CollectionUseCase(get()) }
    single { SearchCollectionUseCase(get()) }
    single { SearchPhotoUseCase(get()) }
    single { GetHistoryUseCase(get()) }
    single { InsertHistoryUseCase(get()) }
    single { DeleteHistoryUseCase(get()) }
    single { DiscoverPhotoUseCase(get()) }
}
