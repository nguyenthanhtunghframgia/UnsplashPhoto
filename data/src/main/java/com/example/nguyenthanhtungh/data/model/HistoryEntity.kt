package com.example.nguyenthanhtungh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.History

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val query: String? = null
) : ModelEntity()

class HistoryEntityMapper : EntityMapper<History, HistoryEntity> {
    override fun mapToDomain(entity: HistoryEntity) = History(
        id = entity.id,
        query = entity.query
    )

    override fun mapToEntity(model: History) = HistoryEntity(
        id = model.id,
        query = model.query
    )
}
