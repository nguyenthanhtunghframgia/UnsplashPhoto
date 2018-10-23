package com.example.nguyenthanhtungh.unsplashphoto

import android.app.Application
import com.example.data.di.repositoryModule
import com.example.nguyenthanhtungh.data.di.apiModule
import com.example.nguyenthanhtungh.unsplashphoto.di.appModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            appModule, apiModule, repositoryModule
        ))
    }
}
