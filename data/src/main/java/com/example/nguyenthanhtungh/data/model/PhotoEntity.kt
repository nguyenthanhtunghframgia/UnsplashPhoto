package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.Photo
import com.google.gson.annotations.SerializedName

class PhotoEntity(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("user")
    val user: UserEntity? = null,
    @SerializedName("urls")
    val urls: UrlsEntity? = null,
    @SerializedName("width")
    val width: Int? = null,
    @SerializedName("height")
    val height: Int? = null
) : ModelEntity()

class PhotoEntityMapper(
    private val userEntityMapper: UserEntityMapper,
    private val urlsEntityMapper: UrlsEntityMapper
) : EntityMapper<Photo?, PhotoEntity?> {

    override fun mapToDomain(entity: PhotoEntity?): Photo? = Photo(
        id = entity?.id,
        description = entity?.description,
        user = userEntityMapper.mapToDomain(entity?.user),
        urls = urlsEntityMapper.mapToDomain(entity?.urls),
        width = entity?.width,
        height = entity?.height
    )

    override fun mapToEntity(model: Photo?): PhotoEntity? = PhotoEntity(
        id = model?.id,
        description = model?.description,
        user = userEntityMapper.mapToEntity(model?.user),
        urls = urlsEntityMapper.mapToEntity(model?.urls),
        width = model?.width,
        height = model?.height
    )
}
