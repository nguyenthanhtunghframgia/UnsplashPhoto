package com.example.nguyenthanhtungh.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nguyenthanhtungh.data.model.HistoryEntity
import com.example.nguyenthanhtungh.data.source.local.dao.HistoryDao

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)

abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private const val DB_NAME: String = "movie_db"

        fun getAppDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java, DB_NAME
            )
                .build()
        }
    }
}
