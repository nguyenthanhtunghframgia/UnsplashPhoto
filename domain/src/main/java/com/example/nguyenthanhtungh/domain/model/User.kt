package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

data class User(
    val id: String? = null,
    val name: String? = null,
    val location: String? = null,
    val bio: String? = null
) : BaseModel()
