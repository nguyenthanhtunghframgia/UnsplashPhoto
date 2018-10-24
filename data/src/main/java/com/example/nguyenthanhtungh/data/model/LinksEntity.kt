package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.Links
import com.google.gson.annotations.SerializedName

class LinksEntity(
    @SerializedName("self")
    val self: String? = null,
    @SerializedName("html")
    val html: String? = null,
    @SerializedName("download")
    val download: String? = null,
    @SerializedName("download_location")
    val download_location: String? = null
) : ModelEntity()

class LinksEntityMapper : EntityMapper<Links, LinksEntity> {

    override fun mapToDomain(entity: LinksEntity): Links = Links(
        self = entity.self,
        html = entity.html,
        download = entity.download,
        download_location = entity.download_location
    )

    override fun mapToEntity(model: Links): LinksEntity = LinksEntity(
        self = model.self,
        html = model.html,
        download = model.download,
        download_location = model.download_location
    )
}
