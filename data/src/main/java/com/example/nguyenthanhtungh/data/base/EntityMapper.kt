package com.example.nguyenthanhtungh.data.base

import com.example.nguyenthanhtungh.domain.base.BaseModel

interface EntityMapper<M : BaseModel, E : ModelEntity> {
    fun mapToDomain(entity: E?): M
    fun mapToEntity(model: M?): E
}
