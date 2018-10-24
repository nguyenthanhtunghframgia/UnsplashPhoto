package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.unsplashphoto.model.*
import org.koin.dsl.module.module

val itemMapperModule = module(override = true) {
    single { createUrlsItemMapper() }
    single { createUserItemMapper() }
    single { createCoverPhotoItemMapper(get()) }
    single { createCollectionItemMapper(get()) }
    single { createPhotoItemMapper(get(), get()) }
}

fun createUrlsItemMapper() = UrlsItemMapper()

fun createUserItemMapper() = UserItemMapper()

fun createCoverPhotoItemMapper(urlsItemMapper: UrlsItemMapper) = CoverPhotoItemMapper(urlsItemMapper)

fun createCollectionItemMapper(coverPhotoItemMapper: CoverPhotoItemMapper) =
    CollectionItemMapper(coverPhotoItemMapper)

fun createPhotoItemMapper(userItemMapper: UserItemMapper, urlsItemMapper: UrlsItemMapper) =
    PhotoItemMapper(userItemMapper, urlsItemMapper)
