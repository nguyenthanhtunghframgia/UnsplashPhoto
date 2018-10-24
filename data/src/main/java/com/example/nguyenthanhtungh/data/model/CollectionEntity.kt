package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.Collection
import com.google.gson.annotations.SerializedName

class CollectionEntity(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("total_photos")
    var totalPhoto: Int? = null,
    @SerializedName("cover_photo")
    var coverPhoto: CoverPhotoEntity? = null
) : ModelEntity()

class CollectionEntityMapper(
    private val coverPhotoEntityMapper: CoverPhotoEntityMapper
) : EntityMapper<Collection, CollectionEntity> {

    override fun mapToDomain(entity: CollectionEntity): Collection {
        return Collection(
            id = entity.id,
            title = entity.title,
            totalPhoto = entity.totalPhoto,
            coverPhoto = coverPhotoEntityMapper.mapToDomain(entity.coverPhoto ?: return Collection())
        )
    }

    override fun mapToEntity(model: Collection): CollectionEntity {
        return CollectionEntity(
            id = model.id,
            title = model.title,
            totalPhoto = model.totalPhoto,
            coverPhoto = coverPhotoEntityMapper.mapToEntity(model.coverPhoto ?: return CollectionEntity())
        )
    }
}
