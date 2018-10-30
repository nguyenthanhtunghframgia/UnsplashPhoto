package com.example.nguyenthanhtungh.domain.repository

import com.example.nguyenthanhtungh.domain.model.Photo
import io.reactivex.Single

interface PhotoRepository {
    fun getListPhoto(id: String, page: Int): Single<List<Photo>>

    fun getListDiscoverPhoto(page: Int): Single<List<Photo>>

    fun searchListPhoto(query: String, page: Int): Single<List<Photo>>
}
