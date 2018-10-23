package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

class Collection(
    val id: String? = null,
    val title: String? = null,
    val totalPhoto: Int? = null,
    val urls: Urls? = null
) : BaseModel()
