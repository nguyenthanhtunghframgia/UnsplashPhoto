package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.*
import org.koin.dsl.module.module

val entityMapperModule = module(override = true) {
    single { createLinksEntityMapper() }
    single { createUrlsEntityMapper() }
    single { createUserEntityMapper() }
    single { createCoverPhotoEntityMapper(get()) }
    single { createCollectionEntityMapper(get()) }
    single { createPhotoEntityMapper(get(), get(), get()) }
}

fun createLinksEntityMapper() = LinksEntityMapper()

fun createUrlsEntityMapper() = UrlsEntityMapper()

fun createUserEntityMapper() = UserEntityMapper()

fun createCoverPhotoEntityMapper(urlsEntityMapper: UrlsEntityMapper) = CoverPhotoEntityMapper(urlsEntityMapper)

fun createCollectionEntityMapper(coverPhotoEntityMapper: CoverPhotoEntityMapper) =
    CollectionEntityMapper(coverPhotoEntityMapper)

fun createPhotoEntityMapper(
    linksEntityMapper: LinksEntityMapper,
    userEntityMapper: UserEntityMapper,
    urlsEntityMapper: UrlsEntityMapper
) = PhotoEntityMapper(linksEntityMapper, userEntityMapper, urlsEntityMapper)
