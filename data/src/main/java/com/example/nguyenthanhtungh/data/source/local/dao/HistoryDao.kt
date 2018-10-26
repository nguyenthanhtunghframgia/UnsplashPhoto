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
    fun insertHistory(historyEntity: HistoryEntity)

    @Query("DELETE FROM history where id NOT IN (SELECT id from history ORDER BY id DESC LIMIT 5)")
    fun limitRecord()

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 5")
    fun getHistory(): Single<List<HistoryEntity>>

    @Query("DELETE FROM history")
    fun deleteAll()
}
