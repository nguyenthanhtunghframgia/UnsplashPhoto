package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.unsplashphoto.model.*
import org.koin.dsl.module.module

val itemMapperModule = module(override = true) {
    single { createLinksItemMapper() }
    single { createUrlsItemMapper() }
    single { createUserItemMapper() }
    single { createCoverPhotoItemMapper(get()) }
    single { createCollectionItemMapper(get()) }
    single { createPhotoItemMapper(get(), get(), get()) }
}

fun createLinksItemMapper() = LinksItemMapper()

fun createUrlsItemMapper() = UrlsItemMapper()

fun createUserItemMapper() = UserItemMapper()

fun createCoverPhotoItemMapper(urlsItemMapper: UrlsItemMapper) = CoverPhotoItemMapper(urlsItemMapper)

fun createCollectionItemMapper(coverPhotoItemMapper: CoverPhotoItemMapper) =
    CollectionItemMapper(coverPhotoItemMapper)

fun createPhotoItemMapper(
    linksItemMapper: LinksItemMapper,
    userItemMapper: UserItemMapper,
    urlsItemMapper: UrlsItemMapper
) = PhotoItemMapper(linksItemMapper, userItemMapper, urlsItemMapper)
