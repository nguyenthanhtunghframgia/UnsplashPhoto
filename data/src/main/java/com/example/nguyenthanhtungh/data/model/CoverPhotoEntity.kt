package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.CoverPhoto
import com.google.gson.annotations.SerializedName

class CoverPhotoEntity(
    @SerializedName("urls")
    val urls: UrlsEntity? = null
) : ModelEntity()

class CoverPhotoEntityMapper(
    private val urlsEntityMapper: UrlsEntityMapper
) : EntityMapper<CoverPhoto?, CoverPhotoEntity?> {

    override fun mapToDomain(entity: CoverPhotoEntity?): CoverPhoto? = CoverPhoto(
        urls = urlsEntityMapper.mapToDomain(entity?.urls)
    )

    override fun mapToEntity(model: CoverPhoto?): CoverPhotoEntity? = CoverPhotoEntity(
        urls = urlsEntityMapper.mapToEntity(model?.urls)
    )
}
