package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.Photo
import com.google.gson.annotations.SerializedName

class PhotoEntity(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("user")
    var user: UserEntity? = null,
    @SerializedName("urls")
    var urls: UrlsEntity? = null,
    @SerializedName("width")
    var width: Int? = null,
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("links")
    var linksEntity: LinksEntity? = null
) : ModelEntity()

class PhotoEntityMapper(
    private val linksEntityMapper: LinksEntityMapper,
    private val userEntityMapper: UserEntityMapper,
    private val urlsEntityMapper: UrlsEntityMapper
) : EntityMapper<Photo, PhotoEntity> {

    override fun mapToDomain(entity: PhotoEntity): Photo {
        return Photo(
            id = entity.id,
            description = entity.description,
            user = userEntityMapper.mapToDomain(entity.user ?: return Photo()),
            urls = urlsEntityMapper.mapToDomain(entity.urls ?: return Photo()),
            width = entity.width,
            height = entity.height,
            links = linksEntityMapper.mapToDomain(entity.linksEntity ?: return Photo())
        )
    }

    override fun mapToEntity(model: Photo): PhotoEntity {
        return PhotoEntity(
            id = model.id,
            description = model.description,
            user = userEntityMapper.mapToEntity(model.user ?: return PhotoEntity()),
            urls = urlsEntityMapper.mapToEntity(model.urls ?: return PhotoEntity()),
            width = model.width,
            height = model.height,
            linksEntity = linksEntityMapper.mapToEntity(model.links ?: return PhotoEntity())
        )
    }
}
