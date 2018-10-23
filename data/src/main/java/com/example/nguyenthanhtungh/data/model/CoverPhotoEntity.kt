package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.google.gson.annotations.SerializedName

class CoverPhotoEntity(
    @SerializedName("urls")
    val urls: UrlsEntity? = null
) : ModelEntity()
