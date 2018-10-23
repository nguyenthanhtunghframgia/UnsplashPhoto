package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.Urls
import com.google.gson.annotations.SerializedName

class UrlsEntity(
    @SerializedName("small")
    val small: String? = null,
    @SerializedName("thumb")
    val thumb: String? = null,
    @SerializedName("raw")
    val raw: String? = null,
    @SerializedName("regular")
    val regular: String? = null,
    @SerializedName("full")
    val full: String? = null
) : ModelEntity()

class UrlsEntityMapper : EntityMapper<Urls, UrlsEntity> {

    override fun mapToDomain(entity: UrlsEntity) = Urls(
        small = entity.small,
        thumb = entity.thumb,
        raw = entity.raw,
        regular = entity.regular,
        full = entity.full
    )

    override fun mapToEntity(model: Urls) = UrlsEntity(
        small = model.small,
        thumb = model.thumb,
        raw = model.raw,
        regular = model.regular,
        full = model.full
    )
}
