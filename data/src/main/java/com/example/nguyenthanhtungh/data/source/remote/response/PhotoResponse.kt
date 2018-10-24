package com.example.nguyenthanhtungh.data.source.remote.response

import com.example.nguyenthanhtungh.data.model.PhotoEntity
import com.google.gson.annotations.SerializedName

open class PhotoResponse(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("total_pages") val totalPage: Int? = null,
    @SerializedName("results") val listPhotos: List<PhotoEntity>? = null
)
