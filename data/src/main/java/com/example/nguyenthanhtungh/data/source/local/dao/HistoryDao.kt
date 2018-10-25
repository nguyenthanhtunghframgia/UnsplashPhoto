package com.example.nguyenthanhtungh.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nguyenthanhtungh.data.model.HistoryEntity
import io.reactivex.Single

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(listHistory: List<HistoryEntity>)

    @Query("SELECT * FROM history LIMIT 5")
    fun getHistory(): Single<List<HistoryEntity>>
}
