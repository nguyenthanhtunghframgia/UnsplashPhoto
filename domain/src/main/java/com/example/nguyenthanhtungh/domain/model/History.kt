package com.example.nguyenthanhtungh.domain.model

import com.example.nguyenthanhtungh.domain.base.BaseModel

data class History(
    val id: Int,
    val query: String? = null
) : BaseModel()
