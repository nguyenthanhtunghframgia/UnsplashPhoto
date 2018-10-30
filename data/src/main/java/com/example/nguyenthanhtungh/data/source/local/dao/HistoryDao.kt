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
    fun insertHistory(historyEntity: HistoryEntity): Long

    @Query("DELETE FROM history where id NOT IN (SELECT id from history ORDER BY id DESC LIMIT 10)")
    fun limitRecord() : Int

    @Query("SELECT * FROM history")
    fun getHistory(): Single<List<HistoryEntity>>

    @Query("DELETE FROM history")
    fun deleteAll() : Int
}
