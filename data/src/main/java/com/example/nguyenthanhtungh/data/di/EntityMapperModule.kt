package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.*
import org.koin.dsl.module.module

val entityMapperModule = module(override = true) {
    single { createUrlsEntityMapper() }
    single { createUserEntityMapper() }
    single { createCoverPhotoEntityMapper(get()) }
    single { createCollectionEntityMapper(get()) }
    single { createPhotoEntityMapper(get(), get()) }
}

fun createUrlsEntityMapper() = UrlsEntityMapper()

fun createUserEntityMapper() = UserEntityMapper()

fun createCoverPhotoEntityMapper(urlsEntityMapper: UrlsEntityMapper) = CoverPhotoEntityMapper(urlsEntityMapper)

fun createCollectionEntityMapper(coverPhotoEntityMapper: CoverPhotoEntityMapper) =
    CollectionEntityMapper(coverPhotoEntityMapper)

fun createPhotoEntityMapper(userEntityMapper: UserEntityMapper, urlsEntityMapper: UrlsEntityMapper) =
    PhotoEntityMapper(userEntityMapper, urlsEntityMapper)
