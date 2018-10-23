package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.Collection
import com.google.gson.annotations.SerializedName

class CollectionEntity(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("total_photos")
    val totalPhoto: Int? = null,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhotoEntity? = null
) : ModelEntity()

class CollectionEntityMapper(
    private val coverPhotoEntityMapper: CoverPhotoEntityMapper
) : EntityMapper<Collection, CollectionEntity> {

    override fun mapToDomain(entity: CollectionEntity?) = Collection(
        id = entity?.id,
        title = entity?.title,
        totalPhoto = entity?.totalPhoto,
        coverPhoto = coverPhotoEntityMapper.mapToDomain(entity?.coverPhoto)
    )

    override fun mapToEntity(model: Collection?) = CollectionEntity(
        id = model?.id,
        title = model?.title,
        totalPhoto = model?.totalPhoto,
        coverPhoto = coverPhotoEntityMapper.mapToEntity(model?.coverPhoto)
    )
}
