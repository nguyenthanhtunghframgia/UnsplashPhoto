package com.example.nguyenthanhtungh.unsplashphoto.base

import com.example.nguyenthanhtungh.domain.base.BaseModel

interface ItemMapper<M : BaseModel, MI : ModelItem> {
    fun mapToPresentation(model: M): ModelItem

    fun mapToDomain(modelItem: MI): BaseModel
}
