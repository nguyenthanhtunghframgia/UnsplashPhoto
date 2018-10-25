package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

class Photo(
    val id: String = "",
    val description: String? = null,
    val user: User? = null,
    val urls: Urls? = null,
    val width: Int? = null,
    val height: Int? = null,
    val links: Links? = null
) : BaseModel()
