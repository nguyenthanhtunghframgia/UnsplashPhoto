package com.example.nguyenthanhtungh.domain.repository

import com.example.nguyenthanhtungh.domain.model.Photo
import io.reactivex.Single

interface PhotoRepository {
    fun getListPhoto(id: String): Single<List<Photo>>
}
