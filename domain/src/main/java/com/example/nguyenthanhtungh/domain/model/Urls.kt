package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

class Urls(
    val small: String? = null,
    val thumb: String? = null,
    val raw: String? = null,
    val regular: String? = null,
    val full: String? = null
) : BaseModel()
