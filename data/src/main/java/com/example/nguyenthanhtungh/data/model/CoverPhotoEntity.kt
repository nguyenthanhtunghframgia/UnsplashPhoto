package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.CoverPhoto
import com.google.gson.annotations.SerializedName

data class CoverPhotoEntity(
    @SerializedName("urls")
    val urls: UrlsEntity? = null
) : ModelEntity()

class CoverPhotoEntityMapper(
    private val urlsEntityMapper: UrlsEntityMapper
) : EntityMapper<CoverPhoto, CoverPhotoEntity> {

    override fun mapToDomain(entity: CoverPhotoEntity): CoverPhoto {
        return CoverPhoto(
            urls = urlsEntityMapper.mapToDomain(entity.urls ?: return CoverPhoto())
        )
    }

    override fun mapToEntity(model: CoverPhoto): CoverPhotoEntity {
        return CoverPhotoEntity(
            urls = urlsEntityMapper.mapToEntity(model.urls ?: return CoverPhotoEntity())
        )
    }
}
