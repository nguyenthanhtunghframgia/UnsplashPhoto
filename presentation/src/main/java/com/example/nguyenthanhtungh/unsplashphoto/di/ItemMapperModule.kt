package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.unsplashphoto.model.*
import org.koin.dsl.module.module

val itemMapperModule = module(override = true) {
    single { LinksItemMapper() }
    single { UrlsItemMapper() }
    single { UserItemMapper() }
    single { HistoryItemMapper() }
    single { CoverPhotoItemMapper(get()) }
    single { CollectionItemMapper(get()) }
    single { PhotoItemMapper(get(), get(), get()) }
}
