package com.example.nguyenthanhtungh.data.di

import android.content.Context
import com.example.nguyenthanhtungh.data.source.local.db.AppDataBase
import org.koin.dsl.module.module

val databaseModule = module(override = true) {
    single { createAppDatabase(get()) }
    single { createHistoryDao(get()) }
}

fun createAppDatabase(context: Context) = AppDataBase.getAppDatabase(context)

fun createHistoryDao(appDatabase: AppDataBase) = appDatabase.historyDao()
