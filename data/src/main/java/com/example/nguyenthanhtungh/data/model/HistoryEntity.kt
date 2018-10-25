package com.example.nguyenthanhtungh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.SearchHistory

@Entity(tableName = "history")
class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    val query: String = ""
) : ModelEntity()

class HistoryEntityMapper : EntityMapper<SearchHistory, HistoryEntity> {
    override fun mapToDomain(entity: HistoryEntity) = SearchHistory(
        id = entity.id,
        query = entity.query
    )

    override fun mapToEntity(model: SearchHistory) = HistoryEntity(
        id = model.id,
        query = model.query
    )
}
