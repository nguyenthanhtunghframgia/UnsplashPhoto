package com.example.nguyenthanhtungh.data.di

import com.example.nguyenthanhtungh.data.model.*
import org.koin.dsl.module.module

val entityMapperModule = module(override = true) {
    single { LinksEntityMapper() }
    single { UrlsEntityMapper() }
    single { UserEntityMapper() }
    single { CoverPhotoEntityMapper(get()) }
    single { CollectionEntityMapper(get()) }
    single { PhotoEntityMapper(get(), get(), get()) }
}
