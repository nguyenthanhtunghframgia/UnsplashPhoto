package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.*
import org.koin.dsl.module.module

val entityMapperModule = module(override = true) {
    single { LinksEntityMapper() }
    single { UrlsEntityMapper() }
    single { UserEntityMapper() }
    single { createCoverPhotoEntityMapper(get()) }
    single { createCollectionEntityMapper(get()) }
    single { createPhotoEntityMapper(get(), get(), get()) }
}

fun createCoverPhotoEntityMapper(urlsEntityMapper: UrlsEntityMapper) = CoverPhotoEntityMapper(urlsEntityMapper)

fun createCollectionEntityMapper(coverPhotoEntityMapper: CoverPhotoEntityMapper) =
    CollectionEntityMapper(coverPhotoEntityMapper)

fun createPhotoEntityMapper(
    linksEntityMapper: LinksEntityMapper,
    userEntityMapper: UserEntityMapper,
    urlsEntityMapper: UrlsEntityMapper
) = PhotoEntityMapper(linksEntityMapper, userEntityMapper, urlsEntityMapper)
