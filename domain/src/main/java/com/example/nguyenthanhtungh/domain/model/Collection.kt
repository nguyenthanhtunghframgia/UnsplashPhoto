package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

class Collection(
    val id: String = "",
    val title: String? = null,
    val totalPhoto: Int? = null,
    val coverPhoto: CoverPhoto? = null
) : BaseModel()
