package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

class Links(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    val download_location: String? = null
) : BaseModel()
