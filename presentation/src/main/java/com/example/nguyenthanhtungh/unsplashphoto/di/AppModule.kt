package com.example.nguyenthanhtungh.unsplashphoto.di

import android.app.Application
import android.content.res.Resources
import org.koin.dsl.module.module

val appModule = module(override = true) {
    single { createResource(get()) }
}

fun createResource(application: Application): Resources = application.resources
