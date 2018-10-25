package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.data.model.LinksEntityMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.*
import org.koin.dsl.module.module

val itemMapperModule = module(override = true) {
    single { LinksEntityMapper() }
    single { UrlsItemMapper() }
    single { UserItemMapper() }
    single { createCoverPhotoItemMapper(get()) }
    single { createCollectionItemMapper(get()) }
    single { createPhotoItemMapper(get(), get(), get()) }
}

fun createCoverPhotoItemMapper(urlsItemMapper: UrlsItemMapper) = CoverPhotoItemMapper(urlsItemMapper)

fun createCollectionItemMapper(coverPhotoItemMapper: CoverPhotoItemMapper) =
    CollectionItemMapper(coverPhotoItemMapper)

fun createPhotoItemMapper(
    linksItemMapper: LinksItemMapper,
    userItemMapper: UserItemMapper,
    urlsItemMapper: UrlsItemMapper
) = PhotoItemMapper(linksItemMapper, userItemMapper, urlsItemMapper)
