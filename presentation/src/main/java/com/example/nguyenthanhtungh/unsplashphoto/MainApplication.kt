package com.example.nguyenthanhtungh.unsplashphoto

import android.app.Application
import com.example.nguyenthanhtungh.data.di.apiModule
import com.example.nguyenthanhtungh.data.di.databaseModule
import com.example.nguyenthanhtungh.data.di.entityMapperModule
import com.example.nguyenthanhtungh.data.di.repositoryModule
import com.example.nguyenthanhtungh.unsplashphoto.di.appModule
import com.example.nguyenthanhtungh.unsplashphoto.di.itemMapperModule
import com.example.nguyenthanhtungh.unsplashphoto.di.useCaseModule
import com.example.nguyenthanhtungh.unsplashphoto.di.viewModelModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                appModule, apiModule, databaseModule, entityMapperModule, itemMapperModule, repositoryModule,
                useCaseModule, viewModelModule
            )
        )
    }
}
