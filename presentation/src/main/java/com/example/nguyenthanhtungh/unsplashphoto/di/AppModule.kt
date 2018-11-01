package com.example.nguyenthanhtungh.unsplashphoto.di

import android.app.Application
import android.content.res.Resources
import com.example.nguyenthanhtungh.unsplashphoto.rx.AppSchedulerProvider
import com.example.nguyenthanhtungh.unsplashphoto.rx.SchedulerProvider
import org.koin.dsl.module.module

val appModule = module(override = true) {
    single<SchedulerProvider> { AppSchedulerProvider() }

    single { createResource(get()) }
}

fun createResource(application: Application): Resources = application.resources
