package com.example.nguyenthanhtungh.data.source.remote.response

import com.example.nguyenthanhtungh.data.model.CollectionEntity
import com.google.gson.annotations.SerializedName

open class CollectionResponse(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("total_pages") val totalPage: Int? = null,
    @SerializedName("results") val listCollection: List<CollectionEntity>? = null
)
